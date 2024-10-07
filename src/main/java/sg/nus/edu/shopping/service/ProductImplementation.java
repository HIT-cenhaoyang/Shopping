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
    public Product findByProductId(int productId) {
       c
        return productRepo.findByProductId(productId);
    }

    //CRUD for Product
    public Product createProduct(Product product) {
        // search if product already exists using sku attribute
        Product existingProduct = productRepo.findBySku(product.getSku());
        if (existingProduct != null) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists.");
        }
        return productRepo.save(product);
    }

    public Product getProductBySku(String sku) {
        if (productRepo.findBySku(sku) == null) {
            throw new IllegalArgumentException("Product with SKU " + sku + " does not exist.");
        }
        return productRepo.findBySku(sku);
    }
    public Product updateProduct(int productId, Product updatedProduct) {
        Product existingProduct = productRepo.findByProductId(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exists.");
        }
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
        Product product = productRepo.findByProductId(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        productRepo.delete(product);
    }
}
