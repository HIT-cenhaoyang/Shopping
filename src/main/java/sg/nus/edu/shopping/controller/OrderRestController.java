package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.nus.edu.shopping.interfacemethods.OrderDetailInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.interfacemethods.PurchaseRecordInterface;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.PurchaseRecord;
import sg.nus.edu.shopping.repository.PurchaseRecordRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderRestController {
    @Autowired
    private PurchaseRecordInterface orderInt;
    @Autowired
    private ProductInterface productInt;

    @GetMapping("/orders")
    public ResponseEntity<List<PurchaseRecord>> findAllOrders(){
       List<PurchaseRecord> allOrders = orderInt.findAllOrders();
       if(!allOrders.isEmpty()) {
           return new ResponseEntity<>(allOrders, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PurchaseRecord> findOrderById(@PathVariable("orderId") int id){
        Optional<PurchaseRecord> optOrder= orderInt.findByPurchaseRecordId(id);
        if (optOrder.isPresent()) {
           PurchaseRecord order = optOrder.get();
           return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<List<PurchaseRecord>> findOrdersByCustomerId(@PathVariable("customerId") String id){
        List<PurchaseRecord> orderListByCust = orderInt.findByCustomerId(id);
        if (!orderListByCust.isEmpty()) {
            return new ResponseEntity<>(orderListByCust, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/date/{date}")
    public ResponseEntity<List<PurchaseRecord>> findOrdersByDate(@PathVariable("date") Date date){
        List<PurchaseRecord> orderListByDate = orderInt.findByDate(date);
        if (!orderListByDate.isEmpty()) {
            return new ResponseEntity<>(orderListByDate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/product/{productId}")
    public ResponseEntity<List<PurchaseRecord>> findOrdersByProduct(@PathVariable("productId") int id) {
        Optional<Product> optProduct = productInt.findByProductId(id);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = optProduct.get();
        List<PurchaseRecord> orderListByProduct = orderInt.findByProduct(product);
        if (!orderListByProduct.isEmpty()) {
            return new ResponseEntity<>(orderListByProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
