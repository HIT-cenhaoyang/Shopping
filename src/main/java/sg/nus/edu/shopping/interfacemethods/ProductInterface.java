package sg.nus.edu.shopping.interfacemethods;


import org.springframework.data.domain.Page;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

//Author: Xu Ziyi, Hannah
public interface ProductInterface {

    List<Product> findAllProducts();
    List<Product> getProductByCategory(Category category);
    List<Product> searchProductsByKeyword(String keyword);
    Optional<Product> findByProductId(int productId);
    Product createProduct(Product product);
    Product updateProduct(int productId, Product updatedProduct);
    void deleteProduct(int productId);
    Page<Product> getProducts(Pageable pageable);
    Page<Product> getProductByCategory(Category category, Pageable pageable);
    Optional<Product> getProductBySku(String sku);
    void saveProduct(Product product);
    List<Product> filterProductsByPrice(Double minPrice, Double maxPrice);
    Page<Product> getLiveProducts(Pageable pageable);
    Page<Product> getLiveProductsByCategory(Category category, Pageable pageable);
    List<Product> filterLiveProductsByPrice(Double minPrice, Double maxPrice);

}
