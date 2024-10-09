package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.nus.edu.shopping.interfacemethods.OrderDetailInterface;
import sg.nus.edu.shopping.interfacemethods.PurchaseRecordInterface;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.PurchaseRecordRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderRestController {
    @Autowired
    private PurchaseRecordInterface orderInt;
    @Autowired
    private OrderDetailInterface orderDetailInt;

    @GetMapping("/orders")
    public List<PurchaseRecord> findAllOrders(){
        return orderInt.findAllOrders();
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<PurchaseRecord> findOrderById(@PathVariable("orderId") int id){
        Optional<PurchaseRecord> optOrder= orderInt.findByPurchaseRecordId(id);
        if (optOrder.isPresent()) {
           PurchaseRecord order = optOrder.get();
           return new ResponseEntity<PurchaseRecord>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /*
    @GetMapping("/orders/product/{id}")
    public ResponseEntity<PurchaseRecord> findOrderbyProduct(@PathVariable("productId") int id){
        List<PurchaseRecord> orderListByProduct = orderInt.
    }*/


}
