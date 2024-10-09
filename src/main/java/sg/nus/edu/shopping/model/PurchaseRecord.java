package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.Date;
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

    private Date date;
    private double orderTotal;

    public PurchaseRecord() {}

    public PurchaseRecord(Customer customer, List<OrderDetail> orderDetails) {
        this.customer = customer;
        this.orderDetails = orderDetails;
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

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    public Date getPurchaseDate() {
        return date;
    }
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    public double getOrderTotal() {
        for (OrderDetail orderDetail : orderDetails) {
            orderTotal+= orderDetail.getOrderSubTotal();
        }
        return orderTotal;
    }
}
