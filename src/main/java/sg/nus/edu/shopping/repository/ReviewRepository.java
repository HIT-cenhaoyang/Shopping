package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.nus.edu.shopping.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductProductId(int productId);
}
