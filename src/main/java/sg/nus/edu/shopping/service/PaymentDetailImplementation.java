package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.PaymentInterface;
import sg.nus.edu.shopping.model.PaymentDetail;
import sg.nus.edu.shopping.repository.PaymentDetailRepository;

@Service
@Transactional
public class PaymentDetailImplementation implements PaymentInterface {

        @Autowired
        PaymentDetailRepository paymentDetailRepo;

        @Override
        public void savePaymentDetails(PaymentDetail paymentDetail) {
                paymentDetailRepo.save(paymentDetail);
        }
}
