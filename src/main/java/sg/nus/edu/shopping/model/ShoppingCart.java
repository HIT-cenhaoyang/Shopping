package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @ManyToOne
    private Product product_cart;

    @ManyToOne
    private Customer cart_customer;

    private int productQty;


    public ShoppingCart() {

    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}
