package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Product;

import java.util.List;

public interface ProductServiceInterface {
    List<Product> getAllProducts();
    Product findProductById(String productId);
}
