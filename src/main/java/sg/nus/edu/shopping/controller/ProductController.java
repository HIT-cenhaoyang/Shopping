package sg.nus.edu.shopping.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.repository.ProductImageRepository;
import sg.nus.edu.shopping.service.CategoryImplementation;
import sg.nus.edu.shopping.service.ProductImplementation;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController{

    @Autowired
    private ProductInterface productInt;

    @Autowired
    private CategoryInterface categoryInt;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    public void setProductInterface(ProductImplementation productImp) {
        this.productInt = productImp;
    }

    @Autowired
    public void setCategoryInterface(CategoryImplementation categoryImp) {
        this.categoryInt = categoryImp;
    }

//    @GetMapping("/products")
//    public String showProducts(@RequestParam(value = "category", required = false) Integer categoryId,
//                               @RequestParam(defaultValue = "1") int page,
//                               @RequestParam(defaultValue = "10") int size,
//                               Model model) {
//        if(page == 0){
//            List<Product> products;
//            if (categoryId != null) {
//                Category category = categoryInt.findByCategoryId(categoryId);
//                products = productInt.getProductByCategory(category);
//            } else {
//                products = productInt.findAllProducts(); // 假设你有一个方法来获取所有产品
//            }
//            model.addAttribute("products", products);
//        }else{
//            Pageable pageable = PageRequest.of(page - 1, size);
//            Page<Product> productPage;
//            if (categoryId != null) {
//                Category category = categoryInt.findByCategoryId(categoryId);
//                productPage = productInt.getProductByCategory(category, pageable);
//            } else {
//                productPage = productInt.getProducts(pageable);
//            }
//            model.addAttribute("products", productPage.getContent()); // 当前页的产品
//            model.addAttribute("currentPage", page); // 当前页码
//            model.addAttribute("totalPages", productPage.getTotalPages()); // 总页数
//            model.addAttribute("totalItems", productPage.getTotalElements()); // 总产品数量
//        }
//
//        return "homePage";
//    }

    @GetMapping("/products")
    public String showProducts(@RequestParam(value = "category", required = false) Integer categoryId,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Model model) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> productPage;
        if (categoryId != null) {
            Category category = categoryInt.findByCategoryId(categoryId);
            productPage = productInt.getProductByCategory(category, pageable);
            model.addAttribute("categoryId", categoryId); // 添加 categoryId 到模型
        } else {
            productPage = productInt.getProducts(pageable);
            model.addAttribute("pageName", "mainPage"); // 添加 pageName 属性
        }

        // 获取指定 imageid 的图片（热销商品的图片）
        List<Integer> hotImageIds = Arrays.asList( 20, 30,  50);
        List<String> hotProductImages = productImageRepository.findFilenamesByIds(hotImageIds);
        // 获取热销商品数据
        model.addAttribute("products", productPage.getContent()); // 当前页的产品
        model.addAttribute("currentPage", page); // 当前页码
        model.addAttribute("totalPages", productPage.getTotalPages()); // 总页数
        model.addAttribute("totalItems", productPage.getTotalElements()); // 总产品数量
        model.addAttribute("hotProducts", hotProductImages);  // 热销商品图片

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

    @GetMapping("/contact")
    public String contact() {
        return "contactPage"; // Ensure you have a contactPage.html template
    }

    @GetMapping("/about")
    public String about() {
        return "aboutPage"; // Ensure you have an aboutPage.html template
    }

    @GetMapping("/delivery")
    public String delivery() {
        return "deliveryPage"; // Ensure you have a deliveryPage.html template
    }

    @GetMapping("/returns")
    public String returns() {
        return "returnsPage"; // Ensure you have a returnsPage.html template
    }

    @GetMapping("/faqs")
    public String faqs() {
        return "faqsPage"; // Ensure you have a faqsPage.html template
    }






}
