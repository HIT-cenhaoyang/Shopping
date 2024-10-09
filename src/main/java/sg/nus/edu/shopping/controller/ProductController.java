package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.service.CategoryImplementation;
import sg.nus.edu.shopping.service.ProductImplementation;
import sg.nus.edu.shopping.repository.ProductRepository;
import java.util.List;

@Controller
public class ProductController{

    @Autowired
    private ProductInterface productInt;

    @Autowired
    private CategoryInterface categoryInt;

    @Autowired
    public void setProductInterface(ProductImplementation productImp) {
        this.productInt = productImp;
    }

    @Autowired
    public void setCategoryInterface(CategoryImplementation categoryImp) {
        this.categoryInt = categoryImp;
    }

    @GetMapping("/products")
    public String showProducts(@RequestParam(value = "category", required = false) Integer categoryId, Model model) {
        List<Product> products;
        if (categoryId != null) {
            Category category = categoryInt.findByCategoryId(categoryId);
            products = productInt.getProductByCategory(category);
        } else {
            products = productInt.findAllProducts(); // 假设你有一个方法来获取所有产品
        }
        model.addAttribute("products", products);
        return "homePage";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productInt.searchProductsByKeyword(keyword);
        if (products.isEmpty()) {
            model.addAttribute("message", "No products found");
        }
        model.addAttribute("products", products);
        return "homePage";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cartPage"; // 假设你有一个名为 cartPage.html 的模板
    }

    @GetMapping("/product/{id}")
    public String showProductDetails(@PathVariable("id") int productId, Model model) {
        Product product = productInt.findByProductId(productId);
        if (product == null) {
            model.addAttribute("errorMessage", "Product not found");
            return "errorPage"; // 假设有一个名为 errorPage.html 的模板
        }
        model.addAttribute("product", product);
        return "productDetails"; // 假设有一个名为 productDetails.html 的模板
    }
        @Autowired
        private ProductRepository productrepository;
        @RequestMapping("/product")
        public String getProduct(Model model) {
            model.addAttribute("product",productrepository.findAll());
            return"purchesRecord";
        }

    }





