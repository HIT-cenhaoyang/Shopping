package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.interfacemethods.ShoppingCartInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.Review;
import sg.nus.edu.shopping.model.ShoppingCart;
import sg.nus.edu.shopping.repository.CustomerRepository;
import sg.nus.edu.shopping.repository.ProductImageRepository;
import sg.nus.edu.shopping.service.CategoryImplementation;
import sg.nus.edu.shopping.service.ProductImplementation;
import sg.nus.edu.shopping.service.ReviewImplementation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import sg.nus.edu.shopping.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Controller

//Author: Xu Ziyi, Htet Inzali
public class ProductController {
	//BPC Update
	@Autowired
	private ReviewImplementation reviewService;
	
	//BPC Update
	@Autowired
    private CustomerRepository customerRepo; // To get the logged-in customer

	@Autowired
	private ProductInterface productInt;

	@Autowired
	private CategoryInterface categoryInt;
	@Autowired
	private ProductImageRepository productImageRepository;


    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ShoppingCartInterface cartService;

    @Autowired
    public void setProductInterface(ProductImplementation productImp) {
        this.productInt = productImp;
    }

    @Autowired
    public void setCategoryInterface(CategoryImplementation categoryImp) {
        this.categoryInt = categoryImp;
    }

    @GetMapping("/filterProducts")
    public ResponseEntity<List<Product>> filterProducts(@RequestParam(required = false) Double minPrice,
                                                        @RequestParam(required = false) Double maxPrice) {
        if (minPrice == null) {
            minPrice = 0.0;
        }
        if (maxPrice == null) {
            maxPrice = Double.MAX_VALUE;
        }
        List<Product> products = productInt.filterLiveProductsByPrice(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products")
    public String showProducts(@RequestParam(value = "category", required = false) Integer categoryId,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               HttpSession sessionObj,
                               Model model) {
       
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> productPage;
        if (categoryId != null) {
            Category category = categoryInt.findByCategoryId(categoryId);
            productPage = productInt.getLiveProductsByCategory(category, pageable);
            model.addAttribute("categoryId", categoryId);
        } else {
            productPage = productInt.getLiveProducts(pageable);
            model.addAttribute("pageName", "mainPage");
        }

        if (page == 1 && "mainPage".equals(model.getAttribute("pageName"))) {
            File imageFolder = new File("src/main/resources/static/images/hotProducts");
            File[] imageFiles = imageFolder.listFiles();

            List<String> hotProductImages = new ArrayList<>();
            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    hotProductImages.add("/images/hotProducts/" + imageFile.getName());
                }
            }

            model.addAttribute("hotProducts", hotProductImages);
            model.addAttribute("currentPage", 1);
            model.addAttribute("pageName", "mainPage");
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        int totalPages = productPage.getTotalPages() > 0 ? productPage.getTotalPages() : 1;
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", productPage.getTotalElements());
		return "homePage";
	}
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> productPage = productRepository.searchLiveProductsByKeyword(keyword, pageable);
        if (productPage.isEmpty()) {
            model.addAttribute("message", "No products found");
        }
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        int totalPages = productPage.getTotalPages() > 0 ? productPage.getTotalPages() : 1;
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        return "homePage";
    }

    @GetMapping("/7haven/cart")
    public String cart(HttpSession sessionObj, Model model) {
    	String customerId = (String) sessionObj.getAttribute("customerId");
    	List<ShoppingCart> cartList = cartService.getCartByCustomerId(customerId);

        model.addAttribute("cart", cartList);
        return "cartPage";
    }

    @GetMapping("/product/{id}")
    public String showProductDetails(@PathVariable("id") int productId, Model model) {
        Optional<Product> optProduct = productInt.findByProductId(productId);
        if (optProduct.isEmpty()) {
            model.addAttribute("errorMessage", "Product not found");
            return "errorPage";
        }
        Product product = optProduct.get();
        model.addAttribute("product", product);
        model.addAttribute("coverImage", product.getCoverImagePath());
        model.addAttribute("additionalImages", product.getAdditionalImages());

        // Fetch and add reviews to the model
        List<Review> reviews = reviewService.findByProductId(productId);
        model.addAttribute("reviews", reviews);

        // find Average Star of product
        Double averageStar = reviewService.findAverageStarByProductId(productId);
        model.addAttribute("averageStar", averageStar != null ? averageStar : 0);

        return "productDetails";

    }

	//BPC Update
	@PostMapping("/reviews/add")
	public String addReview(@RequestParam("productId") int productId, @RequestParam("comment") String comment, HttpSession session, Model model) {
	    Customer customer = getLoggedInCustomer(session); // Get logged-in customer from session

	    if (customer == null) {
	        // Handle case where the customer is not logged in
	        return "redirect:/login"; // Redirect to login page
	    }

	    // Find the product by ID
	    Optional<Product> productOpt = productInt.findByProductId(productId);
	    if (productOpt.isPresent()) {
	        Review review = new Review(comment, productOpt.get(), customer);
	        reviewService.saveReview(review);
	    }

	    // Redirect back to the product details page
	    return "redirect:/product/" + productId;
	}

	//BPC Update
	private Customer getLoggedInCustomer(HttpSession session) {
	    String customerId = (String) session.getAttribute("loggedInCustomerId");
	    if (customerId != null) {
	        return customerRepo.findCustomerById(customerId).orElse(null); // Use findCustomerById
	    }
	    return null; // Return null if customerId is not found in session
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
        return "deliverPage"; // Ensure you have a deliveryPage.html template
    }

	@GetMapping("/returns")
	public String returns() {
		return "returnsPage"; // Ensure you have a returnsPage.html template
	}

	@GetMapping("/faqs")
	public String faqs() {
		return "faqsPage"; // Ensure you have a faqsPage.html template
	}

	@Autowired
	private ProductRepository productrepository;

	@RequestMapping("/product")
	public String getProduct(Model model) {
		model.addAttribute("product", productrepository.findAll());
		return "purchesRecord";
	}

}
