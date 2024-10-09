package sg.nus.edu.shopping.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.edu.shopping.repository.CustomerRepository;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.repository.PurchaseRecordRepository;
import sg.nus.edu.shopping.model.PurchaseRecord;

@Controller
public class PurchaseRecordController {
    @Autowired
    private PurchaseRecordRepository purchaseRecordRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/purchase_record")
    public String getpurchase_record(Model model) {
        //1.according to customerId find record
        String customerId = "z123";
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            List<PurchaseRecord> purchaseRecords = purchaseRecordRepository.findByCustomer(customer);
            customer.setPurchaseRecords(purchaseRecords);
        }
        model.addAttribute("customer",customer);
        return"PurchaseRecord";
    }

}
