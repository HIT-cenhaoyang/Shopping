package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Review;

import java.util.List;

public interface ReviewInterface {
    List<Review> findByProductId(int productId);
}
