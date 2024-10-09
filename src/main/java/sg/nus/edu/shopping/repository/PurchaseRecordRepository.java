package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.PurchaseRecord;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Integer> {
    List<PurchaseRecord> findAll();
    Optional<PurchaseRecord> findByOrderId(int orderId);
    List<PurchaseRecord> findByCustomerId(String customerId);
    List<PurchaseRecord> findByDate(Date date);

    public List<PurchaseRecord> findByCustomer(Customer customer);
}
