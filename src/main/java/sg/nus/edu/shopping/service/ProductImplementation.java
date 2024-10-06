package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.repository.ProductRepository;
import java.util.List;
@Service
@Transactional
public class ProductImplementation implements ProductInterface {
    @Autowired
    private ProductRepository prepro;

    @Override
    @Transactional
    public List<Product> findAll() {
        return prepro.findAll();
    }

    @Override
    @Transactional
    public List<Product> getProductByCategory(Category category) {
        return prepro.getProductByCategory(category);
    }

    @Override
    @Transactional
    public List<Product> searchProductsByKeyword(String keyword) {
        return prepro.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional
    public Product findById(int productId) {
        return prepro.findById(productId).get();
    }
}
