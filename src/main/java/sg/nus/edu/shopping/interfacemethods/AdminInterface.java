package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AdminInterface {

	Optional<Admin> findAdminByUserName(String userName);
    public void saveProduct(Product product);
    public List<Product> findAllProducts();
    public String findMaxAdminId();
}
