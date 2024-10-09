package sg.nus.edu.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.edu.shopping.interfacemethods.OrderDetailInterface;
import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailImplementation implements OrderDetailInterface {
    @Autowired
    private OrderDetailRepository orderDetailRepo;

    public List<OrderDetail> findAll() {
        return orderDetailRepo.findAll();
    }
    @Override
    public Optional<OrderDetail> findByOrderDetailId(int id) {
        Optional<OrderDetail> optOrderDetail = orderDetailRepo.findByOrderDetailId(id);
        if (optOrderDetail.isEmpty()) {
            throw new IllegalArgumentException("Order detail with ID " + id + " does not exist.");
        }
        return optOrderDetail;
    }

    public List<OrderDetail> findByProduct(Product product) {
        if(orderDetailRepo.findByProduct(product) == null) {
            throw new IllegalArgumentException("No order detail records found for Product with ID " + product.getProductId());
        }
        return orderDetailRepo.findByProduct(product);
    }

    public List<OrderDetail> findByPurchaseRecord(PurchaseRecord order) {
        if(orderDetailRepo.findByPurchaseRecord(order) == null) {
            throw new IllegalArgumentException("Order Detail with Id " + order.getOrderId() + " does not exist.");
        }
        return orderDetailRepo.findByPurchaseRecord(order);
    }

}
