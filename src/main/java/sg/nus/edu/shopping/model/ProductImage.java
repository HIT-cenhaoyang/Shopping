package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
//Author: Hannah
public class ProductImage {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int imageId;
    private String fileName;
    private boolean isCoverImage;
    @ManyToOne @JoinColumn(name = "productId")
    @JsonIgnore
    private Product product;

    public ProductImage() {
    }
    public ProductImage(Product product, String fileName, boolean isCoverImage) {
        this.setProduct(product);
        this.setFileName(fileName);
        this.setCoverImage(isCoverImage);
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isCoverImage() {
        return isCoverImage;
    }

    public void setCoverImage(boolean isCoverImage) {
        this.isCoverImage = isCoverImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @Override
    public String toString() {
        return "Image: " + getImageId() + ", Product= " + getProduct().getProductId();
    }

}



