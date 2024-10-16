package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;

import java.util.List;
import java.util.Optional;

// Author: Hannah
public interface CategoryInterface {
    Category findByCategoryId(Integer categoryId);
    Category createCategory(String categoryName);
    Category findByCategoryName(String categoryName);
    List<Category> getAllCategories();
    Category updateCategory(int categoryId, Category updatedCategory);
    void deleteCategory(int categoryId);
    Category findByProductsContaining(Product product);
    List<Category> findAll();
    Category save(Category category);
}
