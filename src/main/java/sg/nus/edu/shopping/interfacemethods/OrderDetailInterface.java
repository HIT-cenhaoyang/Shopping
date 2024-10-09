package sg.nus.edu.shopping.interfacemethods;

import org.springframework.beans.factory.annotation.Autowired;
import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailInterface{

    List<OrderDetail> findAll();
    Optional<OrderDetail> findByOrderDetailId(int id);
    List<OrderDetail> findByProductId(int id);
    List<OrderDetail> findByOrderId(int id);
}
