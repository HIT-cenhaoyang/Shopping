package sg.nus.edu.shopping.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.repository.CustomerRepository;

@Service
@Transactional
public class CustomerImplementation implements CustomerInterface{
	@Autowired
	CustomerRepository customerRepo;
	
	@Override
	@Transactional
    public boolean isUsernameTaken(String userName) {
        return customerRepo.existsByUserName(userName);
    }
	
	@Override
	@Transactional
	public void saveCustomer(Customer customer) {

		customerRepo.save(customer);

	}
	
	@Override
	@Transactional
	public ArrayList<Customer> findCustomerByuserName(String userName) {
		return customerRepo.findCustomerByuserName(userName);
	}

	//Author: xu zhiye
	@Override
	public Customer searchUserByUserName(String userName) {
		// TODO Auto-generated method stub
		if(userName!=null) {
			ArrayList<Customer> userList = customerRepo.searchUserByUserName(userName);
			if(userList!=null && userList.size() > 0) {
				return userList.get(0);
			}
		}
		return null;

	}

	//Author: xu zhiye
	@Override
	public Customer searchUserByUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		if(userEmail!=null) {
			ArrayList<Customer> emailList=customerRepo.searchUserByUserEmail(userEmail);
			if(emailList !=null && emailList.size()>0) {
				return emailList.get(0);
			}
		}
		return null;
	}

	//Author: xu zhiye
	@Override
	public Customer findById(String id) {
		// TODO Auto-generated method stub
		Optional<Customer> result = customerRepo.findById(id);
		if(result.isPresent()) {
			return result.get();
		}else {
			return null;
		}
	}
}
