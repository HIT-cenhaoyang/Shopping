package sg.nus.edu.shopping.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.iss.register.interfacemethods.CustomerInterface;
import sg.nus.iss.register.model.Customer;
import sg.nus.iss.register.repository.CustomerRepository;

@Service
@Transactional
public class CustomerImplementation implements CustomerInterface{
	@Autowired
	CustomerRepository precu;
	
	@Override
	@Transactional
    public boolean isUsernameTaken(String userName) {
        return precu.existsByUserName(userName);
    }
	
	@Override
	@Transactional
	public void saveCustomer(Customer customer) {

		precu.save(customer);

	}
	
	@Override
	@Transactional
	public ArrayList<Customer> findCustomerByuserName(String userName) {
		return precu.findCustomerByuserName(userName);
	}

}
