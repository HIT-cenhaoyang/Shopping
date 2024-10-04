package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
