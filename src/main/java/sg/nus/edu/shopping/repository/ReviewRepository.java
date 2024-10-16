package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Review;

import java.util.List;
//Author: Liu Zheyi
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductProductId(int productId);

    @Query("SELECT AVG(r.star) FROM Review r WHERE r.product.productId = :productId")
    Double findAverageStarByProductId(@Param("productId") int productId);
}
