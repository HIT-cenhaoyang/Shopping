package sg.nus.edu.shopping.interfacemethods;
import sg.nus.edu.shopping.model.ShoppingCart;

import java.util.List;


public interface ShoppingCartInterface {
    List<ShoppingCart> getCartByCustomerId(String customerId);
    List<ShoppingCart> getCartbyCustomerUsername(String username);
    ShoppingCart updateQuantity(int cartId, int quantity);
    void removeProduct(int cartId);
    ShoppingCart addProduct(String customerId, int productId, int quantity);
}
