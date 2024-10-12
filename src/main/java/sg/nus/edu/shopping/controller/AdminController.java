package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.model.Category;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ProductImage;
import sg.nus.edu.shopping.service.AdminImplementation;
import sg.nus.edu.shopping.service.CategoryImplementation;
import sg.nus.edu.shopping.service.ProductImageImplementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminInterface uservice;

    @Autowired
    private ProductImageImplementation productImageImplementation;

    @Autowired
    private CategoryImplementation categoryImplementation;

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
        List<Category> categoryList = categoryImplementation.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "products/list";  // return to the product list page
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryImplementation.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "products/list";  // return to the product list page
    }

    @PostMapping("/products/create")
    public String createProduct(@RequestParam("name") String name,
                                @RequestParam("category") int categoryId,
                                @RequestParam("price") double price,
                                @RequestParam("stockAvailable") int stockAvailable,
                                @RequestParam("description") String description,
                                @RequestParam("sku") String sku,
                                @RequestParam("cover_image") MultipartFile coverImage,
                                @RequestParam("images") MultipartFile[] images,
                                @RequestParam("cover_image_url") String coverImageUrl,
                                @RequestParam("images_url") String imagesUrl,
                                @RequestParam("uploadOption") String uploadOption) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(categoryImplementation.findByCategoryId(categoryId));
        product.setPrice(price);
        product.setStockAvailable(stockAvailable);
        product.setSku(sku);
        product.setDescription(description);

        // Save product first
        uservice.saveProduct(product);

        List<ProductImage> productImages = new ArrayList<>();
        if ("file".equals(uploadOption)) {
            if (!coverImage.isEmpty()) {
                try {
                    String folder = "src/main/resources/static/images/";
                    String originalFilename = coverImage.getOriginalFilename();
                    String extension = getFileExtension(originalFilename);
                    String categoryName = categoryImplementation.findByCategoryId(categoryId).getCategoryName();
                    String newFilename = categoryName + "_" + name + "_cover" + extension;
                    Path path = Paths.get(folder + newFilename);

                    // check if the directory exists, if not create it
                    if (!Files.exists(path.getParent())) {
                        Files.createDirectories(path.getParent());
                    }

                    // save file to resources folder
                    byte[] bytes = coverImage.getBytes();
                    Files.write(path, bytes);

                    // create ProductImage object and set path
                    ProductImage productImage = new ProductImage();
                    productImage.setFileName("localhost:8080/images/" + newFilename);
                    productImage.setCoverImage(true);
                    productImage.setProduct(product);
                    productImages.add(productImage);
                    productImageImplementation.addProductImage(product.getProductId(), productImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            int imageIndex = 1;
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    try {
                        String folder = "src/main/resources/static/images/";
                        String originalFilename = image.getOriginalFilename();
                        String extension = getFileExtension(originalFilename);
                        String categoryName = categoryImplementation.findByCategoryId(categoryId).getCategoryName();
                        String newFilename = categoryName + "_" + name + "_" + imageIndex + extension;
                        Path path = Paths.get(folder + newFilename);

                        // check if the directory exists, if not create it
                        if (!Files.exists(path.getParent())) {
                            Files.createDirectories(path.getParent());
                        }

                        // save file to resources folder
                        byte[] bytes = image.getBytes();
                        Files.write(path, bytes);

                        // create ProductImage object and set path
                        ProductImage productImage = new ProductImage();
                        productImage.setFileName("/images/" + newFilename);
                        productImage.setCoverImage(false);
                        productImage.setProduct(product);
                        productImages.add(productImage);
                        productImageImplementation.addProductImage(product.getProductId(), productImage);

                        imageIndex++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if ("url".equals(uploadOption)) {// 处理封面图片URL
            if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
                ProductImage productImage = new ProductImage();
                productImage.setFileName(coverImageUrl);
                productImage.setProduct(product);
                productImages.add(productImage);
                productImageImplementation.addProductImage(product.getProductId(), productImage);
            }

            // 处理其他图片URL
            if (imagesUrl != null && !imagesUrl.isEmpty()) {
                String[] imageUrls = imagesUrl.split(",");
                for (String imageUrl : imageUrls) {
                    ProductImage productImage = new ProductImage();
                    productImage.setFileName(imageUrl.trim());
                    productImage.setProduct(product);
                    productImages.add(productImage);
                    productImageImplementation.addProductImage(product.getProductId(), productImage);
                }
            }
        }
        product.setImages(productImages);
        uservice.saveProduct(product);

        return "redirect:/adminHomePage/products";
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
/*
    @GetMapping("/adminLogin")
    public String AdminLogin(Model model) {
        model.addAttribute("admin", new Admin());
        return "adminLogin";

    }

    @PostMapping("/validate/adminlogin")
    public String login(Admin user, HttpSession sessionObj, Model model) {
        Admin dataU = uservice.searchUserByUserName(user.getUserName());
        if (dataU == null) {
            model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
            return "adminLogin";
        } else {
            if (dataU.getPassword().equals(user.getPassword())) {
                sessionObj.setAttribute("username", user.getUserName());
                return "redirect:/7haven/list-admin";
            } else {
                model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
                return "adminLogin";
            }
        }
    }

    @GetMapping("/7haven/list-admin")
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
*/

}
