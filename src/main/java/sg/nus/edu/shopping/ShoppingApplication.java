package sg.nus.edu.shopping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.repository.*;
import java.util.List;

@SpringBootApplication
public class ShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runMe(OrderDetailRepository orderDetailRepo, CustomerRepository customerRepo,
//                            PaymentDetailRepository paymentDetailRepo, ProductRepository productRepo,
//                            ShoppingCartRepository shoppingCartRepo, PurchaseRecordRepository purchaseRecordRepo,
//                            AdminRepository adminRepo) {
//        return args -> {
//            //Only for test Only
//            orderDetailRepo.findAll();
//            paymentDetailRepo.findAll();
//            productRepo.findAll();
//            shoppingCartRepo.findAll();
//            purchaseRecordRepo.findAll();
//            adminRepo.findAll();
//        };
//    }
}
