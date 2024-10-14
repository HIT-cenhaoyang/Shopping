package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductRestController {
    @Autowired
    private ProductInterface productInt;

    @Autowired
    private CategoryInterface categoryInt;

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return productInt.findAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable int id) {
        Optional<Product> optProduct = productInt.findByProductId(id);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestParam("name") String name,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("price") double price,
                                                 @RequestParam("stockAvailable") int stockAvailable,
                                                 @RequestParam("sku") String sku,
                                                 @RequestParam("category") String category,
                                                 @RequestParam(value = "cover_image", required = false) MultipartFile coverImage,
                                                 @RequestParam(value = "images", required = false) MultipartFile[] images,
                                                 @RequestParam(value = "cover_image_url", required = false) String coverImageUrl,
                                                 @RequestParam(value = "images_url", required = false) String imagesUrl,
                                                 @RequestParam("uploadOption") String uploadOption) {
        try {
            List<ProductImage> imageEntities = new ArrayList<>();
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockAvailable(stockAvailable);
            product.setSku(sku);
            product.setCategory(categoryInt.findByCategoryName(category));

            // handle cover image
            if ("file".equals(uploadOption) && coverImage != null) {
                String coverImageName = category + "_" + name + "_cover" + getFileExtension(coverImage.getOriginalFilename());
                Path coverImagePath = Paths.get("src/main/resources/static/images/" + coverImageName);
                Files.copy(coverImage.getInputStream(), coverImagePath, StandardCopyOption.REPLACE_EXISTING);
                String coverImageFileName = "localhost:8080/images/" + coverImageName;
                product.addImage(new ProductImage(product, coverImageFileName, true));
            } else if ("url".equals(uploadOption) && coverImageUrl != null) {
                product.addImage(new ProductImage(product, coverImageUrl, true));
            }

            // handle other images
            if ("file".equals(uploadOption) && images != null) {
                int i = 1;
                for (MultipartFile image : images) {
                    String imageName = category + "_" + name + "_" + i + getFileExtension(image.getOriginalFilename());
                    Path imagePath = Paths.get("src/main/resources/static/images/" + imageName);
                    Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    String imageFileName = "localhost:8080/images/" + imageName;
                    imageEntities.add(new ProductImage(product, imageFileName, false));
                    i++;
                }
            } else if ("url".equals(uploadOption) && imagesUrl != null) {
                String[] imageUrls = imagesUrl.split(",");
                for (String imageUrl : imageUrls) {
                    imageEntities.add(new ProductImage(product, imageUrl.trim(), false));
                }
            }

            for (ProductImage image : imageEntities) {
                product.addImage(image);
            }

            Product createdProduct = productInt.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        Optional<Product> optExistingProduct = productInt.findByProductId(id);
        if (optExistingProduct.isPresent()) {
            Product updatedProduct = optExistingProduct.get();
            Category category = categoryInt.findByCategoryId(product.getCategory().getCategoryId());
            if (category!=null) {
                updatedProduct.setCategory(category);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setCategory(product.getCategory());
            updatedProduct.setSku(product.getSku());
            updatedProduct.setImages(product.getImages());
            updatedProduct.setStockAvailable(product.getStockAvailable());
            updatedProduct.setLive(product.isLive());

            //create new list if null. This is to prevent react.js from reporting null error
            if (updatedProduct.getImages() == null) {
                updatedProduct.setImages(new ArrayList<>());
            }
            if (updatedProduct.getShoppingCarts() == null) {
                updatedProduct.setShoppingCarts(new ArrayList<>());
            }
            if (updatedProduct.getOrders() == null) {
                updatedProduct.setOrders(new ArrayList<>());
            }
            if (updatedProduct.getReviews() == null) {
                updatedProduct.setReviews(new ArrayList<>());
            }

            productInt.updateProduct(id, updatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
        try {
            productInt.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
