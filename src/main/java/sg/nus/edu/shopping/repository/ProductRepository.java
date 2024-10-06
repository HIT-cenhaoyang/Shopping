package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySku (String sku);
    Product findByProductId(int productId);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    public List<Product> getProductByCategory(@Param("category") Category category);

    public List<Product> findByNameContainingIgnoreCase(String keyword);


}
