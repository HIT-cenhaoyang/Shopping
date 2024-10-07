package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.ShoppingCart;

import java.util.List;
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findByProductId(int productId);
    List<ShoppingCart> findByCustomerId(String customerId);
    List<ShoppingCart> findByCustomerUserName(String username);
    ShoppingCart getByCartId(int cartId);
    Customer findCustomerByCartId(int cartId);
}





