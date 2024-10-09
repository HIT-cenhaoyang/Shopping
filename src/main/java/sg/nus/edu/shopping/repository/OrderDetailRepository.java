package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    Optional<OrderDetail> findByOrderDetailId (int id);
    List<OrderDetail> findAll();
    List<OrderDetail> findByProduct(Product product);
    List<OrderDetail> findByPurchaseRecord(PurchaseRecord order);

}
