package sg.nus.edu.shopping.model;

import jakarta.persistence.*;


@Entity
public class OrderDetail {

    @Id
    private int orderDetailId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private PurchaseRecord purchaseRecord;

    private int productQty;

    public OrderDetail() {

    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
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
