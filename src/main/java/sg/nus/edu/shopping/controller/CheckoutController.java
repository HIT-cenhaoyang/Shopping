package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.edu.shopping.interfacemethods.*;
import sg.nus.edu.shopping.model.*;
import sg.nus.edu.shopping.service.CustomerImplementation;

import java.time.LocalDate;
import java.util.*;

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
    public void setProductService(ProductInterface pServiceImpl) {this.pService = pServiceImpl;}

    @GetMapping("/7haven/checkout")
    public String checkout(HttpSession sessionObj, Model model) {
        String customerId = (String) sessionObj.getAttribute("customerId");

        Customer customer = cService.findById(customerId);
        List<ShoppingCart> cartList = cartService.getCartByCustomerId(customerId);

        if(cartList.isEmpty()) {
            return "redirect:/7haven/cart";
        }

        // Check that the quantity of each item is less than or equal to the quantity in stock
        for (ShoppingCart cart : cartList) {
            Product product = cart.getProduct();

            // Check if the inventory is sufficient
            if (cart.getProductQty() > product.getStockAvailable()) {
                model.addAttribute("productImagePath",product.getCoverImagePath());
                model.addAttribute("productName", product.getName());
                model.addAttribute("requiredQty", cart.getProductQty());
                model.addAttribute("availableStock", product.getStockAvailable());

                // If you are out of stock, go to the Stock error page
                return "stockError";
            }
        }

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

        // Gets shopping cart information for the current user
        String customerId = (String) sessionObj.getAttribute("customerId");
        List<ShoppingCart> carts = cartService.getCartByCustomerId(customerId);

        //generate orderDate
        LocalDate orderDate = LocalDate.now();

        //purchaseRecord
        Customer customer = cService.findById(customerId);
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

        // Subtract inventory quantity
        for (ShoppingCart cart : carts) {
            Product product = cart.getProduct();
            product.setStockAvailable(product.getStockAvailable() - cart.getProductQty());
            pService.saveProduct(product);  // update stock
        }

        //delete cart
        cartService.clearCartByCustomer(customer);

        PurchaseRecord purchaseRecord1 = pRService.findLastPurchaseRecordByCustomerName(customer.getUserName());
        model.addAttribute("purchaseRecord", purchaseRecord1);

        return "paymentSuccessfully";
    }

    @PostMapping("/return")
    public String returnToHome(Model model){
        return "homePage";
    }
}
