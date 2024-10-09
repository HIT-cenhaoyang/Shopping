package sg.nus.edu.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.edu.shopping.interfacemethods.PurchaseRecordInterface;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.PurchaseRecordRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseRecordImplementation implements PurchaseRecordInterface {
    @Autowired
    private PurchaseRecordRepository purchaseRecordRepo;

    public List<PurchaseRecord> findAllOrders(){
        return purchaseRecordRepo.findAll();
    }
    public Optional<PurchaseRecord> findByPurchaseRecordId(int id){
        Optional<PurchaseRecord> optOrder = purchaseRecordRepo.findByPurchaseRecordId(id);
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
    public List<PurchaseRecord> findByDate(Date date) {
        if (purchaseRecordRepo.findByDate(date) == null) {
            throw new IllegalArgumentException("No purchase records found for date" + date);
        }
        else return purchaseRecordRepo.findByDate(date);
    }
 }
