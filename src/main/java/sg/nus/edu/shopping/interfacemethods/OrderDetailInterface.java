package sg.nus.edu.shopping.interfacemethods;

import org.springframework.beans.factory.annotation.Autowired;
import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

//Author: Hannah, Liu Zheyi
public interface OrderDetailInterface{

    void saveAllOrderDetail(List<OrderDetail> detail);
    List<OrderDetail> findAll();
    Optional<OrderDetail> findByOrderDetailId(int id);
    List<OrderDetail> findByProduct(Product product);
    List<OrderDetail> findByPurchaseRecord(PurchaseRecord order);
}
