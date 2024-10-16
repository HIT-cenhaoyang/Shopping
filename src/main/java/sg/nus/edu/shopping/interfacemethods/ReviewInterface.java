package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Review;

import java.util.List;

//Author: Liu Zheyi
public interface ReviewInterface {
    List<Review> findByProductId(int productId);
    void saveReview(Review review);
    Double findAverageStarByProductId(int productId);
}