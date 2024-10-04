package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(length = 20)
    private String name;

    private double price;

    @Column(length = 50)
    private String unitMeasurement;

    @Column(length = 10)
    private int stockAvailable;

    @Column(length = 10)
    private String categoryId;

    @Column(length = 50)
    private String description;

    @OneToMany(mappedBy = "product_cart")
    private List<ShoppingCart> shoppingCart;

    @OneToMany
    private List<OrderDetail> detail_product;

    //constructor
    public Product() {

    }

    public Product(String name, double price, String unitMeasurement,
                   int stockAvailable, String categoryId, String description) {
        this.name = name;
        this.price = price;
        this.unitMeasurement = unitMeasurement;
        this.stockAvailable = stockAvailable;
        this.categoryId = categoryId;
        this.description = description;
    }


    //setter and getter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String category) {
        this.categoryId = category;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(int stockAvailbility) {
        this.stockAvailable = stockAvailbility;
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(String unit) {
        this.unitMeasurement = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}