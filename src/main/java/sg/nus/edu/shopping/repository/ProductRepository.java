package sg.nus.edu.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findBySku (String sku);
    Optional<Product> findByProductId(int productId);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> getProductByCategory(@Param("category") Category category);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    Page<Product> getProductByCategory(@Param("category") Category category, Pageable pageable);
}
