package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.shopping.interfacemethods.CategoryInterface;
import sg.nus.edu.shopping.interfacemethods.ProductInterface;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.service.CategoryImplementation;
import sg.nus.edu.shopping.service.ProductImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

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

//	@GetMapping("/products")
//	public String showProducts(@RequestParam(value = "category", required = false) Integer categoryId, Model model) {
//		List<Product> products;
//		if (categoryId != null) {
//			Category category = categoryInt.findByCategoryId(categoryId);
//			products = productInt.getProductByCategory(category);
//		} else {
//			products = productInt.findAllProducts(); // 假设你有一个方法来获取所有产品
//		}
//		model.addAttribute("products", products);
//		return "homePage";
//	}

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

//	@GetMapping("/product/{id}")
//	public String showProductDetails(@PathVariable("id") int productId, Model model) {
//		Optional<Product> optProduct = productInt.findByProductId(productId);
//		if (optProduct.isEmpty()) {
//			model.addAttribute("errorMessage", "Product not found");
//			return "errorPage"; // 假设有一个名为 errorPage.html 的模板
//		}
//		Product product = optProduct.get();
//		model.addAttribute("product", product);
//		// BPC
//		model.addAttribute("coverImage", product.getCoverImagePath()); // For cover image
//		// BPC
//		model.addAttribute("additionalImages", product.getAdditionalImages()); // For other images
//		return "productDetails"; // 假设有一个名为 productDetails.html 的模板
//	}
	
	@GetMapping("/product/{id}")
	public String showProductDetails(@PathVariable("id") int productId, Model model) {
	    Optional<Product> optProduct = productInt.findByProductId(productId);
	    if (optProduct.isEmpty()) {
	        model.addAttribute("errorMessage", "Product not found");
	        return "errorPage";
	    }
	    Product product = optProduct.get();
	    model.addAttribute("product", product);
	    model.addAttribute("coverImage", product.getCoverImagePath()); // For cover image
	    model.addAttribute("additionalImages", product.getAdditionalImages()); // For additional images
	    return "productDetails"; // Assuming you have this template
	}


	@GetMapping("/products")
	public String showProducts(@RequestParam(value = "category", required = false) Integer categoryId, Model model) {
		List<Product> products;
		if (categoryId != null) {
			Category category = categoryInt.findByCategoryId(categoryId);
			products = productInt.getProductByCategory(category);
		} else {
			products = productInt.findAllProducts(); // Ensure this method is returning all products
		}
		model.addAttribute("products", products); // Add products to the model
		return "homePage"; // Return the home page template
	}

	@PostMapping("/cart/add")
	public String addToCart(@RequestParam("productId") int productId, HttpSession session) {
		// Retrieve the product by ID
		Optional<Product> optProduct = productInt.findByProductId(productId);
		if (optProduct.isPresent()) {
			Product product = optProduct.get();

			// Add the product to the cart session (you can modify this based on your cart
			// implementation)
			List<Product> cart = (List<Product>) session.getAttribute("cart");
			if (cart == null) {
				cart = new ArrayList<>();
			}
			cart.add(product);
			session.setAttribute("cart", cart);
		}

		// Redirect to cart page
		return "redirect:/cart";
	}

}
