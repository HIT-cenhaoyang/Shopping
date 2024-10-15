package sg.nus.edu.shopping.interfacemethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sg.nus.edu.shopping.model.Customer;

public interface CustomerInterface {
	//if the username has been taken
	//Author: Xu Ziyi
	public boolean isUsernameTaken(String userName);
	public void saveCustomer(Customer customer);
	
	//Author: xu zhiye
	public Customer searchUserByUserName(String userName);
	public Customer searchUserByUserEmail(String userEmail);
	public Customer findById(String id);

	//Author: Hannah
	Optional<Customer> findCustomerById(String customerId);
	List<Customer> findAllCustomers();
	Optional<Customer> findByUserName(String userName);
	List<Customer> findByBirthDateMonth(int month);

	//Author: Cen Haoyang
	String findMaxCustomerId();
}
