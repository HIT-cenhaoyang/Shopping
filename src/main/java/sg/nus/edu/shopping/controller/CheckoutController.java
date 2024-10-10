package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.interfacemethods.OrderDetailInterface;
import sg.nus.edu.shopping.interfacemethods.PurchaseRecordInterface;
import sg.nus.edu.shopping.interfacemethods.ShoppingCartInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.model.ShoppingCart;
import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.service.CustomerImplementation;

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

    @GetMapping("/checkout")
    public String checkout(HttpSession sessionObj, Model model) {
        String customerName = (String) sessionObj.getAttribute("userName");

        //Customer customer = cService.searchUserByUserName(customerName);
        Customer customer = cService.searchUserByUserName("Wioner");
        List<ShoppingCart> cartList = cartService.getCartByCustomerUsername("Wioner");
        double totalPrice = cartList.stream()
                .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getProductQty())
                .sum();
        model.addAttribute("cart", cartList);
        model.addAttribute("customer", customer);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @PostMapping("/submitOrder")
    public String save(HttpSession sessionObj, Model model) {

        //generate orderDate
        Date orderDate = new Date();

        //purchaseRecord
        String customerName = (String) sessionObj.getAttribute("userName");
        //Customer customer = cService.searchUserByUserName(customerName);
        Customer customer = cService.searchUserByUserName("Wioner");
        PurchaseRecord purchaseRecord = new PurchaseRecord();
        purchaseRecord.setCustomer(customer);
        purchaseRecord.setOrderDate(orderDate);
        pRService.savePurchaseRecord(purchaseRecord);

        //orderDetail
        //List<ShoppingCart> carts = cartService.getCartByCustomerUsername(customerName);
        List<ShoppingCart> carts = cartService.getCartByCustomerUsername("Wioner");
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart item : carts) {
            OrderDetail details = new OrderDetail();
            details.setProduct(item.getProduct());
            details.setProductQty(item.getProductQty());
            details.setPurchaseRecord(purchaseRecord);
            orderDetails.add(details);
        }
        oService.saveAllOrderDetail(orderDetails);

        //delete cart
        scService.clearCartByCustomer(customer);

        return "paymentSuccessfully";
    }

    @PostMapping("/return")
    public String returnToHome(Model model){
        return "homePage";
    }
}
