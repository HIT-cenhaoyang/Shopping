package sg.nus.edu.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.PurchaseRecord;

import java.time.LocalDate;
import java.util.List;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Integer> {
    List<PurchaseRecord> findAll();

    Optional<PurchaseRecord> findByOrderId(int orderId);

    List<PurchaseRecord> findByCustomerId(String customerId);

    List<PurchaseRecord> findByOrderDate(LocalDate date);

    @Query("SELECT p FROM PurchaseRecord p WHERE p.customer = :customer")
    public List<PurchaseRecord> findPurchaseRecordByCustomer(@Param("customer") Customer customer);

    public List<PurchaseRecord> findByCustomer(Customer customer);

    @Query("SELECT p FROM PurchaseRecord p LEFT JOIN FETCH p.orderDetails WHERE p.customer.userName = :customerName AND p.orderId = (SELECT MAX(p2.orderId) FROM PurchaseRecord p2 WHERE p2.customer.userName = :customerName)")
    PurchaseRecord findLatestPurchaseRecordByCustomer(@Param("customerName") String customerName);

    Page<PurchaseRecord> findAllByOrderByOrderIdDesc(Pageable pageable);
}
