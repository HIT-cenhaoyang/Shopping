package sg.nus.edu.shopping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;


@Entity
public class OrderDetail {

    @Id
    private int orderDetailId;

    @ManyToOne
    private Product detail_product;

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
}
