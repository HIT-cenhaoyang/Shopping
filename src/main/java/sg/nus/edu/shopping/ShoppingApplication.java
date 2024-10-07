package sg.nus.edu.shopping;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.repository.*;
import java.util.List;

@SpringBootApplication
public class ShoppingApplication {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ShoppingApplication(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Bean @Transactional @Profile("dev")
    public ApplicationRunner runner(){
        return args -> {
            Category category1=new Category("Category 1");
            Category category2=new Category("Category 2");
            Product product1=new Product("Guitar",1200,"123");
            Product product2=new Product("Radio",1200,"456");
            Product product3=new Product("Chips",1200,"789");
            Product product4=new Product("Phone",1200,"789");

            category1.getProducts().add(product1);
            product1.setCategory(category1);

            category1.getProducts().add(product2);
            product2.setCategory(category1);

            category2.getProducts().add(product3);
            product3.setCategory(category2);

            category2.getProducts().add(product4);
            product4.setCategory(category2);

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);

        };
    }

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