package sg.nus.edu.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import sg.nus.edu.shopping.interfacemethods.ProductImageInterface;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;
import sg.nus.edu.shopping.repository.ProductImageRepository;
import sg.nus.edu.shopping.repository.ProductRepository;

import java.util.List;

public class ProductImageImplementation implements ProductImageInterface {
    @Autowired
    private ProductImageRepository productImageRepo;
    @Autowired
    private ProductRepository productRepo;

    //CRUD for ProductImage
    public void addProductImage(int productId, ProductImage productImage) {
        // check if product exists
        Product existingProduct = productRepo.findByProductId(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        //link image to product
        productImage.setProduct(existingProduct);
        //add image to product
        existingProduct.addImage(productImage);
        //save changes
        productRepo.save(existingProduct);
        productImageRepo.save(productImage);
    }
    public ProductImage getCoverImage(int productId) {
        Product existingProduct = productRepo.findByProductId(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        return existingProduct.getCoverImage();
    }
    public List<ProductImage> getImagesByProduct(int productId) {
        Product existingProduct = productRepo.findByProductId(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        return productImageRepo.findByProduct(existingProduct);
    }

    public ProductImage updateProductImage(int imageId, ProductImage productImage) {
        ProductImage existingImage = productImageRepo.findByImageId(imageId);
        if (existingImage == null) {
            throw new IllegalArgumentException("Image with ID " + imageId + " does not exist.");
        }
        existingImage.setCoverImage(productImage.isCoverImage());
        existingImage.setFileName(productImage.getFileName());
        return productImageRepo.save(existingImage);
    }

    public void deleteProductImage(int productId, int imageId) {
        Product existingProduct = productRepo.findByProductId(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        ProductImage productImage = productImageRepo.findByImageId(imageId);
        if (productImage == null) {
            throw new IllegalArgumentException("Image with ID " + imageId + " does not exist.");
        }
        //remove image from product
        existingProduct.removeImage(productImage);
        //save changes
        productRepo.save(existingProduct);
        productImageRepo.delete(productImage);
    }
}
