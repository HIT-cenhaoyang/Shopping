package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByUserName(String userName);

    Optional<Customer> findCustomerById(String customerId);

    List<Customer> findAll();

    @Query("SELECT c FROM Customer c WHERE MONTH(c.birthDate) = ?1")
    List<Customer> findByBirthDateMonth(int month);

    Optional<Customer> findByUserName(String userName);
    
    //Author: xu zhiye
	@Query("Select c from Customer as c where c.userName =:userName")
	public ArrayList<Customer> searchUserByUserName(@Param("userName") String userName);
	
    //Author: xu zhiye
	@Query("Select c from Customer as c where c.email =:userEmail")
	public ArrayList<Customer> searchUserByUserEmail(@Param("userEmail") String userEmail);

    @Query("SELECT MAX(c.id) FROM Customer c")
    String findMaxCustomerId();
}
