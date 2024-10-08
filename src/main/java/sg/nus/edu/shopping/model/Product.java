package sg.nus.edu.shopping.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Product {
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

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    private String description;

    @OneToMany(mappedBy = "product")
    private List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orders;


    //constructor
    public Product() {
    }

    public Product(String name, double price, String sku) {
        this.name = name;
        this.price = price;
        //to ensure no duplicate products are saved
        this.sku = sku;
    }

    //get images of product
    public List<ProductImage> getImages() {
        return images;
    }

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
}