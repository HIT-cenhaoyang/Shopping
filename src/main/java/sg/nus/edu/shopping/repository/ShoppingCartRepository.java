package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findByCustomerId(String customerId);
    List<ShoppingCart> findByCustomerUsername(String username);
    ShoppingCart getByCartId(int cartId);
    Customer findCustomerByCartId(int cartId);
}
