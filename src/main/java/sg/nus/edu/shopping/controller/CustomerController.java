package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.service.CustomerImplementation;

import java.time.LocalDate;
@Controller
public class CustomerController {

    @Autowired
    private CustomerInterface cusregister;

    @Autowired
    public void setCustomerService(CustomerImplementation cusregisterImp) {
        this.cusregister = cusregisterImp;
    }
    //if the user put in http://localhost/3306/register the browser will open register page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registerPage";
    }

    //if the user put in http://localhost/3306/home the browser will open home page, this where is for after posting register successful to jump to the home page
    @GetMapping("/home")
    public String home() {
        return "homePage";
    }

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    //for receiving the customer register data from front-end
    @PostMapping("/register")
    public String register(
            @RequestParam("name") String name,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
            @RequestParam("gender") String gender,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            Model model
    ) {
        //make sure the user name is unique
        if (cusregister.isUsernameTaken(userName)) {
            model.addAttribute("errorMessage", "Username already taken. Please choose another one.");
            return "registerPage";
        }

        //confirm password the second times
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "registerPage";
        }

        //make sure password is correct format
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,20}$")) {
            model.addAttribute("errorMessage", "Password must contain at least 9 characters at most 20 characters, including uppercase, lowercase letters, and numbers.");
            return "registerPage";
        }

        Customer customer = new Customer();
        customer.setName(name);
        customer.setUserName(userName);
        customer.setPassword(password);
        customer.setBirthDate(birthdate);
        customer.setGender(gender);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        cusregister.saveCustomer(customer);

        model.addAttribute("successMessage", "Registration successful!");
        return "redirect:/home";

    }
}
