package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;
import sg.nus.edu.shopping.repository.AdminRepository;
import sg.nus.edu.shopping.repository.CategoryRepository;
import sg.nus.edu.shopping.repository.ProductImageRepository;
import sg.nus.edu.shopping.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminImplementation implements AdminInterface {

    @Autowired
    AdminRepository adminRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    ProductImageRepository productImageRepo;

    @Override
    public Optional<Admin> findAdminByUserName(String userName) {
        return adminRepo.findAdminByUserName(userName);
    }


    //CRUD for Product
    public Product createProduct(Product product) {
        // search if product already exists using sku attribute
        Optional<Product> existingProduct = productRepo.findBySku(product.getSku());
        if (existingProduct != null) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists.");
        }
        return productRepo.save(product);
    }

    public Optional<Product> getProductById(int productId) {
        if (productRepo.findByProductId(productId) == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        return productRepo.findByProductId(productId);
    }

    public Optional<Product> getProductBySku(String sku) {
        if (productRepo.findBySku(sku) == null) {
            throw new IllegalArgumentException("Product with SKU " + sku + " does not exist.");
        }
        return productRepo.findBySku(sku);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product updateProduct(int productId, Product updatedProduct) {
        Optional<Product> optExistingProduct = productRepo.findByProductId(productId);
        if (optExistingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exists.");
        }
        Product existingProduct = optExistingProduct.get();
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setStockAvailable(updatedProduct.getStockAvailable());
        existingProduct.setImages(updatedProduct.getImages());
        return productRepo.save(existingProduct);
    }

    public void deleteProduct(int productId) {
        Optional<Product> optProduct = productRepo.findByProductId(productId);
        if (optProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        Product product = optProduct.get();
        productRepo.delete(product);
    }

    //CRUD for category
    public Category createCategory(String categoryName) {
        Category existingCategory = categoryRepo.findByCategoryName(categoryName);
        if (existingCategory != null) {
            throw new IllegalArgumentException("Category with name " + categoryName + " already exists.");
        }
        Category newCategory = new Category(categoryName);
        return categoryRepo.save(newCategory);
    }

    public Category getCategoryById(int categoryId) {
        if (categoryRepo.findByCategoryId(categoryId) == null) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
        return categoryRepo.findByCategoryId(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepo.findByCategoryId(categoryId);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setCategoryDescription(updatedCategory.getCategoryDescription());
        return categoryRepo.save(existingCategory);
    }

    public void deleteCategory(int categoryId) {
        Category category = categoryRepo.findByCategoryId(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
        categoryRepo.delete(category);
    }

    //CRUD for ProductImage
    public void addProductImage(int productId, ProductImage productImage) {
        // check if product exists
        Optional<Product> optExistingProduct = productRepo.findByProductId(productId);
        if (optExistingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        Product existingProduct = optExistingProduct.get();
        //link image to product
        productImage.setProduct(existingProduct);
        //add image to product
        existingProduct.addImage(productImage);
        //save changes
        productRepo.save(existingProduct);
        productImageRepo.save(productImage);
    }

    public ProductImage getCoverImage(int productId) {
        Optional<Product> optExistingProduct = productRepo.findByProductId(productId);
        if (optExistingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        Product existingProduct = optExistingProduct.get();
        return existingProduct.getCoverImage();
    }

    public List<ProductImage> getImagesByProduct(int productId) {
        Optional<Product> optExistingProduct = productRepo.findByProductId(productId);
        if (optExistingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        Product existingProduct = optExistingProduct.get();
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
        Optional<Product> optExistingProduct = productRepo.findByProductId(productId);
        if (optExistingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        Product existingProduct = optExistingProduct.get();
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


    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    public String findMaxAdminId() {
        return adminRepo.findMaxAdminId();
    }
}
