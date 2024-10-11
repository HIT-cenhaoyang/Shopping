package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.interfacemethods.PurchaseRecordInterface;
import sg.nus.edu.shopping.model.Customer;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CustomerRestController {
    @Autowired
    private CustomerInterface custInt;

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
}
