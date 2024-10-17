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

//Author: Deng Jingyu, Xu Zhiye
public class ShoppingCartController {

    @Autowired
    private ProductInterface productInt; 

    @Autowired
    private ShoppingCartInterface shoppingCartInt;
    
    @Autowired
    private CustomerInterface customerService;


    //add product to shopping cart
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
    	List<ShoppingCart> cartList = shoppingCartInt.getCartByCustomerId(customerId);
    	
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

    // receive data
    public static class AddToCartRequest {
        private String customerId; 
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
