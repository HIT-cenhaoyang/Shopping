package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.Product;

import java.util.ArrayList;
import java.util.List;

public interface AdminInterface {

	public Admin searchUserByUserName(String userName);
    public void saveProduct(Product product);
    public List<Product> findAllProducts();
}
