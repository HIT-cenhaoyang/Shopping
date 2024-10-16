package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
//Author: Xu Ziyi, Cen Haoyang, Hannah. Liu Zheyi
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String name;

    //unique sku code (e.g barcode)
    private String sku;

    private double price;

    @Column(length = 10)
    private int stockAvailable;

    @ManyToOne @JoinColumn(name = "categoryId")
    private Category category;

    private String dimensions;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductImage> images = new ArrayList<>();

    private String description;

    //to set visibility status of product
    private boolean isLive;


    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orders = new ArrayList<>();;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();;


    //constructor
    public Product() {
        this.images = new ArrayList<>();
        this.shoppingCarts = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.isLive = false;
    }

    public Product(String name, double price, String sku) {
        this.name = name;
        this.price = price;
        //to ensure no duplicate products are saved
        this.sku = sku;
        this.isLive = false;
    }

    //get images of product
    public List<ProductImage> getImages() {
        return images;
    }

    @JsonIgnore
    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
    //removing a product image
    public void removeImage(ProductImage image) {
        //removes image from product image list
        images.remove(image);
        // removes relationship between image and product
        image.setProduct(null);
    }

    //Add new image to the product
    public void addImage(ProductImage image) {
        if (image.isCoverImage()) {
            // If this image is a cover image, set all other images to not be cover image
            this.images.forEach(img -> img.setCoverImage(false));
        } else if (this.images.isEmpty()) {
            // If this is the first image, automatically set it as the cover
            image.setCoverImage(true);
        }
        // add image to product image list
        images.add(image);
        // link image to product
        image.setProduct(this);
    }

    //Get the cover image
    public ProductImage getCoverImage() {
        return images.stream()
                .filter(ProductImage::isCoverImage)
                .findFirst()
                .orElse(null);
    }
    
    //BPC
    public void addImageToProduct(Product product, String fileName, boolean isCoverImage) {
        ProductImage image = new ProductImage(product, fileName, isCoverImage);
        product.addImage(image); // Assuming this method is defined in Product to manage the images list
    }
    
    //BPC
    @JsonIgnore
    public List<ProductImage> getAdditionalImages() {
        return images.stream()
                     .filter(img -> !img.isCoverImage())
                     .collect(Collectors.toList());
    }

    public String getCoverImagePath() {
        return images.stream()
                .filter(ProductImage::isCoverImage)
                .map(ProductImage::getFileName)
                .findFirst()
                .orElse("https://res.cloudinary.com/castlery/image/private/f_auto,q_auto/b_rgb:F3F3F3,c_fit/v1677755266/crusader/variants/T50441097-CA4001-WA/Owen-Left-Chaise-Sectional-Sofa-Haze-Walnut-Square-Set_1-1677755263.jpg"); // 如果没有封面图片，则使用默认图片
    }

    //setter and getter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(int stockAvailbility) {
        this.stockAvailable = stockAvailbility;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public List<OrderDetail> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}