package sg.nus.edu.shopping.interfacemethods;

import java.util.ArrayList;

import sg.nus.iss.register.model.Customer;

public interface CustomerInterface {
	//if the username has been taken
	public boolean isUsernameTaken(String userName);
	//
	public void saveCustomer(Customer customer);
	
	public ArrayList<Customer> findCustomerByuserName(String userName);
}
