package sg.nus.edu.shopping.service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.repository.CategoryRepository;
import sg.nus.edu.shopping.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryImplementation implements CategoryInterface {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ProductRepository productRepo;

    @Override
    public Category findByCategoryId(Integer categoryId) {
        return categoryRepo.findByCategoryId(categoryId);
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName);
    }
    public Category findByProductsContaining(Product product) {
        if(productRepo.findByProductId(product.getProductId()).isEmpty()) {
            throw new IllegalArgumentException("Product does not exist");
        }
        return categoryRepo.findByProductsContaining(product);
    }

    public  List<Category> findAll() {
        return categoryRepo.findAll();
    }
    public Category save(Category category) {
        return categoryRepo.save(category);
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
}
