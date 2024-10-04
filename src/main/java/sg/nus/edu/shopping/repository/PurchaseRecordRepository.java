package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.PurchaseRecord;

public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Integer> {

}
