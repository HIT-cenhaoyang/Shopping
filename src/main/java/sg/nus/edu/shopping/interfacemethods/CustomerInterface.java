package sg.nus.edu.shopping.interfacemethods;

import java.util.ArrayList;
import sg.nus.edu.shopping.model.Customer;

public interface CustomerInterface {
	//if the username has been taken
	public boolean isUsernameTaken(String userName);
	//
	public void saveCustomer(Customer customer);
	
	public ArrayList<Customer> findCustomerByuserName(String userName);
	
	//Author: xu zhiye
	public Customer searchUserByUserName(String userName);
	public Customer searchUserByUserEmail(String userEmail);
	public Customer findById(String id);
}
