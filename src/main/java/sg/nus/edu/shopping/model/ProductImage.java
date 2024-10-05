package sg.nus.edu.shopping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductImage {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int imageId;

    private String path;

    @ManyToOne
    private Product product_image;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
