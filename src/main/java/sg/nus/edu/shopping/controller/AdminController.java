package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;
import sg.nus.edu.shopping.repository.CategoryRepository;
import sg.nus.edu.shopping.repository.ProductImageRepository;
import sg.nus.edu.shopping.service.AdminImplementation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
	@Autowired
	private AdminInterface uservice;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    public void setUserService(AdminImplementation userviceImpl) {
        this.uservice = userviceImpl;
    }

    @GetMapping("/adminHomePage/products")
    public String listProducts(Model model) {
        List<Product> products = uservice.findAllProducts();
        model.addAttribute("products", products);
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "products/list";  // 返回到商品列表页面
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "products/list";  // 返回到商品创建页面
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute Product product, @RequestParam("categoryId") int categoryId,
                                @RequestParam("images") MultipartFile[] files) {
        Category category = categoryRepository.findByCategoryId(categoryId);
        product.setCategory(category);

        // Save product first
        uservice.saveProduct(product);

        // Save images
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            ProductImage image = new ProductImage();
            String newFileName = product.getCategory().getCategoryName() + "_" + product.getName() + "_"
                    + (i + 1) + getFileExtension(file.getOriginalFilename());
            image.setFileName(newFileName);
            image.setProduct(product);

            // Save file to filesystem
            String uploadDir = "src/main/resources/static/images/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            try {
                file.transferTo(new File(uploadDir + newFileName));
                image.setImagePath("/images/" + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            productImages.add(image);
            productImageRepository.save(image);
        }
        product.setImages(productImages);

        return "redirect:/products";
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
	@GetMapping ("/adminLogin")
	public String AdminLogin(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminLogin";
		
	}
	
	@PostMapping("/validate/adminlogin")
	public String login(Admin user, HttpSession sessionObj,Model model) {
		Admin dataU = uservice.searchUserByUserName(user.getUserName());
		if (dataU == null) {
			model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
			return "adminLogin";
		} else {
			if (dataU.getPassword().equals(user.getPassword())) {
				sessionObj.setAttribute("username", user.getUserName());
				return "redirect:/protected/list-admin";
			} else {
				model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
				return "adminLogin";
			}
		}
	}
	
	@GetMapping("/protected/list-admin")
	public String listUsers(HttpSession sessionObj, Model model) {
		String username = (String) sessionObj.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		} else {
			return "adminPanel";
		}
	}
	
	@GetMapping("/adminLogout")
	public String adminLogout(HttpSession sessionObj, Model model) {
		sessionObj.removeAttribute("username");
		return "redirect:/adminLogin";
	}
	
	
}
