package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
    ProductImage findByImageId(int imageId);
}
