package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.interfacemethods.ShoppingCartInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.ShoppingCart;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    @Autowired
    private ProductInterface productInt; // 产品服务接口，获取产品信息

    @Autowired
    private ShoppingCartInterface shoppingCartInt; // 购物车服务接口，管理购物车
    
    @Autowired
    private CustomerInterface customerService;

    /* 获取所有产品的 API Hannah: this method is in product rest controller.
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productInt.findAllProducts();
    }
    */

    // 添加产品到购物车的 API
    @PostMapping("/cart/add")
    public String addProductToCart(@RequestBody AddToCartRequest request) {
        try {
        	ShoppingCart cart = shoppingCartInt.getCartByCustomerIdProductId(request.getCustomerId(), request.getProductId());
        	if(cart != null) {
        		shoppingCartInt.updateQuantity(cart.getCartId(), cart.getProductQty()+request.getQuantity());
        	}else {
        		cart = shoppingCartInt.addProduct(request.getCustomerId(), request.getProductId(), request.getQuantity());
        	}
            return "Product added to cart successfully. Cart ID: " + cart.getCartId();
        } catch (Exception e) {
            return "Error adding product to cart: " + e.getMessage();
        }
    }
    
    @GetMapping("/cart/items")
    public List<ShoppingCart> getShoppingCartList(HttpSession sessionObj){
    	
        String customerId = (String) sessionObj.getAttribute("customerId");

        Customer customer = customerService.findById(customerId);
    	List<ShoppingCart> cartList = shoppingCartInt.getCartByCustomerUsername(customer.getUserName());
    	
    	cartList.forEach(cart -> {
    		System.out.println(cart.getProduct().getName() +" count: "+cart.getProductQty());
    	});
    	 return cartList;
    }
    
    @PutMapping("/cart/update/{cartId}/{quantity}")
    public String updateShoppingCartList(@PathVariable("cartId") int cartId, @PathVariable("quantity") int quantity){
    	try {
  
    		shoppingCartInt.updateQuantity(cartId, quantity);
    	return "New quantity update to cart successfully. Cart ID: " + cartId;
        } catch (Exception e) {
            return "Failed to update quantity to cart: " + e.getMessage();
        }
    }
    
    @DeleteMapping("/cart/remove/{cartId}")
    public String deleteShoppingCart(@PathVariable("cartId") int cartId) {
    	try {
    		shoppingCartInt.removeProduct(cartId);
    		return "Delete item from cart successfully. Cart ID: " + cartId;
        } catch (Exception e) {
            return "Failed to delete item from cart: " + e.getMessage();
        }
    }

    // Data Transfer Object 类 接收从客户端传递的数据
    public static class AddToCartRequest {
        private String customerId; // 添加 customerId 字段
        private int productId;
        private int quantity;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
