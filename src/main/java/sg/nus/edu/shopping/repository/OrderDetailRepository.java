package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    Optional<OrderDetail> findByOrderDetailId (int id);
    List<OrderDetail> findAll();
    List<OrderDetail> findByProductId(int productId);
    List<OrderDetail> findByOrderId(int purchaseRecordId);

}
