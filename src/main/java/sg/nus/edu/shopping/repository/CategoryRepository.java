package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.nus.edu.shopping.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);
    Category findByCategoryId(int categoryId);
}
