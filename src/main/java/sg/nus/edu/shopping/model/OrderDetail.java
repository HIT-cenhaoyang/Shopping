package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailId;

    @ManyToOne @JoinColumn (name = "productId")
    private Product product;

    @ManyToOne @JoinColumn(name = "orderId")
    @JsonIgnore
    private PurchaseRecord purchaseRecord;
    private int productQty;

    public OrderDetail() {
    }

    public int getProductId() {
        int productId = product.getProductId();
        return productId;
    }
    public String getProductName() {
        String productName = product.getName();
        return productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public double getProductPrice() {
        return product.getPrice();
    }
    public double getOrderSubTotal() {
        double subTotal = product.getPrice() * productQty;
        return subTotal;
    }


    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Product getProduct() {
        return product;
    }

    @JsonIgnore
    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseRecord getPurchaseRecord() {
        return purchaseRecord;
    }

    public void setPurchaseRecord(PurchaseRecord purchaseRecord) {
        this.purchaseRecord = purchaseRecord;
    }
}
