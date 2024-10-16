package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.PaymentDetail;

//Author: Cen Haoyang
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String> {
	

}
