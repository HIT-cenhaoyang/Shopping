package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class OrderDetail {

    @Id
    private int orderDetailId;

    @ManyToOne @JoinColumn (name = "productId")
    private Product product;

    @ManyToOne @JoinColumn(name = "orderId")
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
}
