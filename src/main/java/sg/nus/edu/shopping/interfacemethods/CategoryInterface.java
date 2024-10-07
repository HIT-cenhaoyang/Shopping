package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Category;

import java.util.List;

public interface CategoryInterface {
    Category findByCategoryId(Integer categoryId);
    Category createCategory(String categoryName);
    List<Category> getAllCategories();
    Category updateCategory(int categoryId, Category updatedCategory);
    void deleteCategory(int categoryId);
}
