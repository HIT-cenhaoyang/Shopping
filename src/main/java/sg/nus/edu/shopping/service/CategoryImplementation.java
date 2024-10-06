package sg.nus.edu.shopping.service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.repository.CategoryRepository;

@Service
@Transactional
public class CategoryImplementation implements CategoryInterface {
    @Autowired
    private CategoryRepository crepo;

    @Override
    public Category findByCategoryId(Integer categoryId) {
        return crepo.findByCategoryId(categoryId);
    }
}
