package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySku (String sku);
    Product findByProductId(int productId);
}
