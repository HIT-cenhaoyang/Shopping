package sg.nus.edu.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.ProductServiceInterface;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductServiceInterface {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product findProductById(int productId) {
        return productRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
