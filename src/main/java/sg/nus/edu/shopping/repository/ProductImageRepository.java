package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;

import java.util.List;
//Author: Hannah, Xu Ziyi
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
    ProductImage findByImageId(int imageId);
    @Query("SELECT pi.fileName FROM ProductImage pi WHERE pi.imageId IN :ids")
    List<String> findFilenamesByIds(@Param("ids") List<Integer> ids);
}
