package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.edu.shopping.interfacemethods.*;
import sg.nus.edu.shopping.model.*;
import sg.nus.edu.shopping.service.CustomerImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CustomerInterface cService;
    @Autowired
    private ShoppingCartInterface cartService;
    @Autowired
    private PurchaseRecordInterface pRService;
    @Autowired
    private OrderDetailInterface oService;
    @Autowired
    private ShoppingCartInterface scService;
    @Autowired
    private ProductInterface pService;

    @Autowired
    public void setCustomerService(CustomerImplementation cServiceImpl) {
        this.cService = cServiceImpl;
    }
    @Autowired
    public void setCartService(ShoppingCartInterface cartServiceImpl) {
        this.cartService = cartServiceImpl;
    }
    @Autowired
    public void setPRService(PurchaseRecordInterface pRServiceImpl) {
        this.pRService = pRServiceImpl;
    }
    @Autowired
    public void setOrderService(OrderDetailInterface oServiceImpl) {
        this.oService = oServiceImpl;
    }
    @Autowired
    public void setShoppingCartService(ShoppingCartInterface scServiceImpl) {
        this.scService = scServiceImpl;
    }
    @Autowired
    public void setProductService(ProductInterface pServiceImpl) {this.pService = pServiceImpl;}

    @GetMapping("/7haven/checkout")
    public String checkout(HttpSession sessionObj, Model model) {
        String customerName = (String) sessionObj.getAttribute("username");

        Customer customer = cService.searchUserByUserName(customerName);
        List<ShoppingCart> cartList = cartService.getCartByCustomerUsername(customerName);
        double totalPrice = cartList.stream()
                .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getProductQty())
                .sum();
        model.addAttribute("cart", cartList);
        model.addAttribute("customer", customer);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @PostMapping("/submitOrder")
    public String save(@RequestParam("recipientName") String recipientName,
                       @RequestParam("recipientAddress") String recipientAddress,
                       @RequestParam("recipientPhone") String recipientPhone,
                       @RequestParam("cardNumber") String cardNumber,
                       HttpSession sessionObj, Model model) {

        // 获取当前用户的购物车信息
        String username = (String) sessionObj.getAttribute("username");
        List<ShoppingCart> carts = cartService.getCartByCustomerUsername(username);

        // 检查每个商品的数量是否小于等于库存数量
        for (ShoppingCart cart : carts) {
            Product product = cart.getProduct();

            // 检查库存是否足够
            if (cart.getProductQty() > product.getStockAvailable()) {
                model.addAttribute("productName", product.getName());
                model.addAttribute("requiredQty", cart.getProductQty());
                model.addAttribute("availableStock", product.getStockAvailable());

                // 如果库存不足，跳转到库存错误页面
                return "stockError";
            }
        }

        //generate orderDate
        LocalDate orderDate = LocalDate.now();

        //purchaseRecord
        String customerName = (String) sessionObj.getAttribute("username");
        Customer customer = cService.searchUserByUserName(customerName);
        PurchaseRecord purchaseRecord = new PurchaseRecord();
        purchaseRecord.setCustomer(customer);
        purchaseRecord.setOrderDate(orderDate);
        purchaseRecord.setRecipientName(recipientName);
        purchaseRecord.setRecipientAddress(recipientAddress);
        purchaseRecord.setRecipientPhone(recipientPhone);
        purchaseRecord.setPaymentMethod(cardNumber);
        pRService.savePurchaseRecord(purchaseRecord);

        //orderDetail
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart item : carts) {
            OrderDetail details = new OrderDetail();
            details.setProduct(item.getProduct());
            details.setProductQty(item.getProductQty());
            details.setPurchaseRecord(purchaseRecord);
            orderDetails.add(details);
        }
        oService.saveAllOrderDetail(orderDetails);

        // 扣减库存数量
        for (ShoppingCart cart : carts) {
            Product product = cart.getProduct();
            product.setStockAvailable(product.getStockAvailable() - cart.getProductQty());
            pService.saveProduct(product);  // 更新产品库存
        }

        //delete cart
        scService.clearCartByCustomer(customer);

        return "paymentSuccessfully";
    }

    @PostMapping("/return")
    public String returnToHome(Model model){
        return "homePage";
    }
}
