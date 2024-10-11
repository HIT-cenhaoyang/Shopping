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
public class CategoryRestController {
    @Autowired
    private CategoryInterface categoryInt;
    @Autowired
    private ProductInterface productInt;

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
}
