package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;

import java.util.List;

public interface ProductInterface {

    List<Product> findAllProducts();
    List<Product> getProductByCategory(Category category);
    List<Product> searchProductsByKeyword(String keyword);
    Product findByProductId(int productId);
    Product createProduct(Product product);
    Product updateProduct(int productId, Product updatedProduct);
    void deleteProduct(int productId);
    Product getProductBySku(String sku);

}
