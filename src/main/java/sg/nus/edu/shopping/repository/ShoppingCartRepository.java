package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.ShoppingCart;

import java.util.List;
@Repository
//Author: Xu Zhiye
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    //List<ShoppingCart> findByProductId(int productId);
    List<ShoppingCart> findByCustomerId(String customerId);
    List<ShoppingCart> findByCustomerUserName(String username);
    ShoppingCart getByCartId(int cartId);
    Customer findCustomerByCartId(int cartId);

    void deleteByCustomer(Customer customer);
    
    @Query("select sc from ShoppingCart sc where sc.product.productId = :productId and sc.customer.id =:id")
    List<ShoppingCart> getCartByCustomerIdProductId(@Param("id") String id, @Param("productId") int productId);
}





