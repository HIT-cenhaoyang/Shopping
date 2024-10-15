package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseRecordInterface {

    List<PurchaseRecord> findAllOrders();
    Optional<PurchaseRecord> findByPurchaseRecordId(int id);
    List<PurchaseRecord> findByCustomerId(String id);
    List<PurchaseRecord> findByDate(LocalDate date);
    List<PurchaseRecord> findByProduct(Product product);
    List<PurchaseRecord> searchPurchaseRecordByCustomer(Customer customer);
    void savePurchaseRecord(PurchaseRecord purchaseRecord);
    List<PurchaseRecord> findByCustomer(Customer customer);
    PurchaseRecord findLastPurchaseRecordByCustomerName(String customerName);
}
