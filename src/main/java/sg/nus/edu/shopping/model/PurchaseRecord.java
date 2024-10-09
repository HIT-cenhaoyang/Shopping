package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class PurchaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne @JoinColumn (name = "customerId")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "purchaseRecord")
    private List<OrderDetail> orderDetails;

    private Date date;

    public PurchaseRecord() {}

    public PurchaseRecord(Customer customer, Date date, List<OrderDetail> orderDetails) {
        this.customer = customer;
        this.date = date;
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
        double orderTotal = 0;
        for (OrderDetail orderDetail : orderDetails) {
            orderTotal+= orderDetail.getOrderSubTotal();
        }
        return orderTotal;
    }
}
