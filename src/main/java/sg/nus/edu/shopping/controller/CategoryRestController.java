package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")

//Author: Hannah
public class CategoryRestController {
    @Autowired
    private CategoryInterface categoryInt;
    @Autowired
    private ProductInterface productInt;

    // method to retrieve categories can request parameters through categoryId/categoryName/productId
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer productId ) {

        List<Category> categoryResult;
        if (categoryId != null) {
            categoryResult= List.of(categoryInt.findByCategoryId(categoryId));
        } else if (categoryName != null) {
            categoryResult = List.of(categoryInt.findByCategoryName(categoryName));
        } else if (productId != null) {
            Optional<Product> optProduct = productInt.findByProductId(productId);
            Product product = optProduct.orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
            categoryResult = List.of(categoryInt.findByProductsContaining(product));
        } else categoryResult = categoryInt.findAll();

        if (categoryResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryResult, HttpStatus.OK);
        }
    }

    @PostMapping ("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category newCategory) {
        if(categoryInt.findByCategoryName(newCategory.getCategoryName()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Category savedCategory = categoryInt.save(newCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        }
    }

    @PutMapping ("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody Category updatedCategory) {
        Category existingCategory = categoryInt.findByCategoryId(categoryId);
        if (existingCategory != null) {
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            existingCategory.setCategoryDescription(updatedCategory.getCategoryDescription());
            Category savedCategory = categoryInt.save(existingCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        try {
            categoryInt.deleteCategory(categoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
