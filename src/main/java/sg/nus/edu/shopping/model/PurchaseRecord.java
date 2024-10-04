package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PurchaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    private Customer record_customer;

    @OneToMany(mappedBy = "order_purchaseRecord")
    private List<OrderDetail> purchaseRecord_orderDetail;

    public PurchaseRecord() {

    }


    public Customer getRecord_customer() {
        return record_customer;
    }

    public void setRecord_customer(Customer record_customer) {
        this.record_customer = record_customer;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


}
