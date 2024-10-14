package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.repository.PaymentDetailRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private CustomerInterface custInt;
    @Autowired
    private AdminInterface adminInt;
    
    @Autowired
    private PaymentDetailRepository paymentDetailRepo;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer birthMonth) {

        List<Customer> customerResult;
        if(customerId != null) {
            Optional<Customer> optCustomer = custInt.findCustomerById(customerId);
            if(optCustomer.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            customerResult = List.of(optCustomer.get());
        } else if(userName != null) {
            Optional<Customer> optCustomer = custInt.findByUserName(userName);
            if(optCustomer.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            customerResult = List.of(optCustomer.get());
        } else if(birthMonth != null) {
            customerResult = custInt.findByBirthDateMonth(birthMonth);
        } else customerResult = custInt.findAllCustomers();

        if (customerResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerResult, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
        String userName = loginData.get("userName");
        String password = loginData.get("password");
        Optional<Admin> optAdmin = adminInt.findAdminByUserName(userName);
        if (optAdmin.isEmpty()) {
            return new ResponseEntity<>("Username not found.", HttpStatus.NOT_FOUND);
        } else {
            Admin admin = optAdmin.get();
            if (admin.getPassword().equals(password)) {
                return new ResponseEntity<>("Successfully logged in.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password.", HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @PostMapping("/deletePayment")
    public void deletePayment(@RequestParam("cardNumber") String cardNumber) {
    	paymentDetailRepo.deleteById(cardNumber);
    	
    }
}
