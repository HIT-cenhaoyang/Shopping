package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



@Entity
public class OrderDetail {

    @Id
    private int orderDetailId;

    @ManyToOne @JoinColumn (name = "productId")
    private Product product;

    @ManyToOne @JoinColumn(name = "orderId")
    @JsonIgnore
    private PurchaseRecord purchaseRecord;
    private int productQty;

    public OrderDetail() {
    }

    public int getProductQty() {
        return productQty;
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
