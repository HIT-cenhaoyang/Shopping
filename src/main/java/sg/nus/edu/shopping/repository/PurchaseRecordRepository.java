package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.PurchaseRecord;

import java.util.List;

public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Integer> {

    public List<PurchaseRecord> findByCustomer(Customer customer);
}
