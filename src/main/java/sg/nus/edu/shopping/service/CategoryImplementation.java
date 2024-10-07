package sg.nus.edu.shopping.service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.repository.CategoryRepository;

import java.util.List;

@Service
@Transactional
public class CategoryImplementation implements CategoryInterface {
    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Category findByCategoryId(Integer categoryId) {
        if (categoryRepo.findByCategoryId(categoryId) == null) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
        return categoryRepo.findByCategoryId(categoryId);
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
