package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Product;

import java.util.List;
import java.util.Optional;

// Author: Cen HaoYang
public interface AdminInterface {

	Optional<Admin> findAdminByUserName(String userName);
    void saveProduct(Product product);
    List<Product> findAllProducts();
    String findMaxAdminId();
}
