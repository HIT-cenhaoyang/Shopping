package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductImplementation implements ProductInterface {
    @Autowired
    private ProductRepository productRepo;

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    @Transactional
    public List<Product> getProductByCategory(Category category) {
        return productRepo.getProductByCategory(category);
    }

    @Override
    @Transactional
    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepo.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional
    public Optional<Product> findByProductId(int productId) {
        return productRepo.findByProductId(productId);
    }

    //CRUD for Product
    public Product createProduct(Product product) {
        // search if product already exists using sku attribute
        Optional<Product> optExistingProduct = productRepo.findBySku(product.getSku());
        if (optExistingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists.");
        }
        return productRepo.save(product);
    }

    public Optional<Product> getProductBySku(String sku) {
        return productRepo.findBySku(sku);
    }

    public Product updateProduct(int productId, Product updatedProduct) {
        Optional<Product> optExistingProduct = productRepo.findByProductId(productId);
        if (optExistingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exists.");
        }
        Product existingProduct = optExistingProduct.get();
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setStockAvailable(updatedProduct.getStockAvailable());
        existingProduct.setImages(updatedProduct.getImages());

        return productRepo.save(existingProduct);
    }

    public void deleteProduct(int productId) {
        Optional<Product> optProduct = productRepo.findByProductId(productId);
        if (optProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        Product product = optProduct.get();
        productRepo.delete(product);
    }

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<Product> getProductByCategory(Category category, Pageable pageable) {
        return productRepo.getProductByCategory(category, pageable);
    }
}
