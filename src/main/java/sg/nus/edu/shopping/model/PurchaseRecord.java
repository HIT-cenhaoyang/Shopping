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

    //@Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String recipientName;
    private String recipientAddress;
    private String recipientPhone;

    private String paymentMethod;

    @ManyToOne @JoinColumn (name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "purchaseRecord" , fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    public PurchaseRecord() {

    }


    public Customer getCustomer() {
        return customer;
    }

    @JsonIgnore
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustId() {
        String customerId = customer.getCustomerId();
        return customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getOrderTotal() {
        double orderTotal = 0;
        for (OrderDetail orderDetail : orderDetails) {
            orderTotal+= orderDetail.getOrderSubTotal();
        }
        return orderTotal;
    }
}
