package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;

import java.util.List;

public interface ProductInterface {

    public List<Product> findAll();
    public List<Product> getProductByCategory(Category category);
    public List<Product> searchProductsByKeyword(String keyword);

    Product findById(int productId);
}
