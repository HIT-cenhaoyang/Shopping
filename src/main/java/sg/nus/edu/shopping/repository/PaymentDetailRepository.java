package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.PaymentDetail;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String> {

}
