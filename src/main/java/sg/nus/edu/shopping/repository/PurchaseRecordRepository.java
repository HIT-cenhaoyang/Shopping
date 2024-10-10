package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.PurchaseRecord;

import java.util.List;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Integer> {
    List<PurchaseRecord> findAll();
    Optional<PurchaseRecord> findByOrderId(int orderId);
    List<PurchaseRecord> findByCustomerId(String customerId);
    List<PurchaseRecord> findByOrderDate(Date date);

    @Query("SELECT p FROM PurchaseRecord p WHERE p.customer = :customer")
    public List<PurchaseRecord> findPurchaseRecordByCustomer(@Param("customer") Customer customer);
    public List<PurchaseRecord> findByCustomer(Customer customer);
}
