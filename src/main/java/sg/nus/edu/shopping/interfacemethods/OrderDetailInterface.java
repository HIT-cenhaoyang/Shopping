package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;

import java.util.List;
import java.util.Optional;

public interface OrderDetailInterface{

    void saveAllOrderDetail(List<OrderDetail> detail);
    List<OrderDetail> findAll();
    Optional<OrderDetail> findByOrderDetailId(int id);
    List<OrderDetail> findByProduct(Product product);
    List<OrderDetail> findByPurchaseRecord(PurchaseRecord order);
}
