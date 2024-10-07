package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PurchaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "purchaseRecord")
    private List<OrderDetail> orderDetails;

    public PurchaseRecord() {

    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


}
