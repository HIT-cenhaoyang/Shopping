package sg.nus.edu.shopping.interfacemethods;

import org.springframework.beans.factory.annotation.Autowired;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.PurchaseRecordRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseRecordInterface {

    List<PurchaseRecord> findAllOrders();
    Optional<PurchaseRecord> findByPurchaseRecordId(int id);
    List<PurchaseRecord> findByCustomerId(String id);
    List<PurchaseRecord> findByDate(Date date);
}
