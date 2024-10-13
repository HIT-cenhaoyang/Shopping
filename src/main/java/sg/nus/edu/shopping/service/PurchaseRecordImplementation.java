package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.PurchaseRecordInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.OrderDetail;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.OrderDetailRepository;
import sg.nus.edu.shopping.repository.PurchaseRecordRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseRecordImplementation implements PurchaseRecordInterface {
    @Autowired
    private PurchaseRecordRepository purchaseRecordRepo;
    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Override
    @Transactional
    public List<PurchaseRecord> searchPurchaseRecordByCustomer(Customer customer) {
        return purchaseRecordRepo.findPurchaseRecordByCustomer(customer);
    }
    public List<PurchaseRecord> findAllOrders(){
        return purchaseRecordRepo.findAll();
    }
    public Optional<PurchaseRecord> findByPurchaseRecordId(int id){
        Optional<PurchaseRecord> optOrder = purchaseRecordRepo.findByOrderId(id);
        if (optOrder.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + id + " does not exist.");
        }
        return optOrder;
    }
    public List<PurchaseRecord> findByCustomerId(String id) {
        if (purchaseRecordRepo.findByCustomerId(id) == null) {
            throw new IllegalArgumentException("Customer with ID " + id + " does not have any purchase records.");
        }
        else return purchaseRecordRepo.findByCustomerId(id);
    }
    public List<PurchaseRecord> findByDate(LocalDate date) {
        if (purchaseRecordRepo.findByOrderDate(date) == null) {
            throw new IllegalArgumentException("No purchase records found for date" + date);
        }
        else return purchaseRecordRepo.findByOrderDate(date);
    }

    @Override
    @Transactional
    public void savePurchaseRecord(PurchaseRecord purchaseRecord) {
        purchaseRecordRepo.save(purchaseRecord);
    }
    public List<PurchaseRecord> findByProduct(Product product) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByProduct(product);
        List<PurchaseRecord> orderListByProduct = orderDetails.stream().map(OrderDetail::getPurchaseRecord).distinct().collect(Collectors.toList());
        return orderListByProduct;
    }
    @Override
    @Transactional
    public  List<PurchaseRecord> findByCustomer(Customer customer){
        return purchaseRecordRepo.findByCustomer(customer);
    }
}
