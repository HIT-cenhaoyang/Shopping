package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.ReviewInterface;
import sg.nus.edu.shopping.model.Review;
import sg.nus.edu.shopping.repository.ReviewRepository;

import java.util.List;

@Service
@Transactional
public class ReviewImplementation implements ReviewInterface{
    @Autowired
    private ReviewRepository reviewRepo;

    @Transactional
    public List<Review> findByProductId(int productId) {
        return reviewRepo.findByProductProductId(productId);
    }
    @Override
    @Transactional
    public void saveReview(Review review) {
        reviewRepo.save(review);
    }

    @Override
    @Transactional
    public Double findAverageStarByProductId(int productId){
        return reviewRepo.findAverageStarByProductId(productId);
    }


}
