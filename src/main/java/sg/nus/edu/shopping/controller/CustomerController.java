package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.edu.shopping.interfacemethods.CustomerInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.service.CustomerImplementation;
import sg.nus.edu.shopping.service.CustomerValidator;

import java.time.LocalDate;
@Controller
public class CustomerController {

    @Autowired
    private CustomerInterface cusregister;

    @Autowired
    public void setCustomerService(CustomerImplementation cusregisterImp) {
        this.cusregister = cusregisterImp;
    }
    
    //Author: Xu zhiye
	@Autowired
	private CustomerValidator customerValidator;

	//Author: Xu zhiye
	@InitBinder
	private void initCusotmerBinder(WebDataBinder binder) {
		// Other binding
		binder.addValidators(customerValidator);
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
    
    //Author: xu zhiye
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new Customer());
		return "login";
	}
    
	//Author: xu zhiye
	@PostMapping("/validate/login")
	public String login(Customer user, HttpSession sessionObj,Model model) {
		Customer dataU = cusregister.searchUserByUserName(user.getUserName());
		if (dataU == null) {
			model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
			model.addAttribute("user", new Customer());
			return "login";
		} else {
			if (dataU.getPassword().equals(user.getPassword())) {
				sessionObj.setAttribute("username", user.getUserName());
				return "redirect:/protected/list-users";
			} else {
				model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
				model.addAttribute("user", new Customer());
				return "login";
			}
		}
	}

	//Author: xu zhiye
	@GetMapping("/protected/list-users")
	public String listUsers(HttpSession sessionObj, Model model) {
		String username = (String) sessionObj.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		} else {
			return "homePage";
		}
	}
	//I commented out this method because it's the same as the adminlogin method and you don't need to write it again
	//Author: xu zhiye
	/*
		@GetMapping("/logout")
	public String logout(HttpSession sessionObj, Model model) {
		sessionObj.removeAttribute("username");
		return "redirect:/login";
	}
	 */


	//Author: xu zhiye
	@GetMapping("/user/resetPassword")
	public String resetPassword(Model model) {
		model.addAttribute("user", new Customer());

		return "accountVerification";

	}

	//Author: xu zhiye
	@PostMapping("/validateAccount")
	public String validateReset(Customer user, Model model,HttpSession obj) {
		Customer dataU = cusregister.searchUserByUserEmail(user.getEmail());
		if (dataU == null) {
			model.addAttribute("MSG", "Your Verifivation failed, please try again!");
			model.addAttribute("user", new Customer());
			return "accountVerification";
		} else {
			if (user.getName().equalsIgnoreCase(dataU.getName())) {
				Customer c = new Customer();
				c.setUserName(dataU.getUserName());
				model.addAttribute("user", c);
				obj.setAttribute("userId", dataU.getUserId());
				return "resetPassword";
			} else {
				model.addAttribute("MSG", "Your Verifivation failed, please try again!");
				model.addAttribute("user", new Customer());
				return "accountVerification";
			}
		}
	}

	//Author: xu zhiye
	@PostMapping("/validate/reset")
	public String resetP(@Valid @ModelAttribute Customer user, BindingResult bindingResult, Model model, HttpSession obj) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.user", bindingResult);
			model.addAttribute("user", user);
			return "resetPassword";
		}

		String id=(String)obj.getAttribute("userId");
		Customer dataU = cusregister.findById(id);

		// dataU is old user name, confirm no duplicate user name in data base
		if (!dataU.getUserName().equalsIgnoreCase(user.getUserName())
				&& cusregister.searchUserByUserName(user.getUserName()) != null) {
			model.addAttribute("MSG", "User Name exist, please try again!");
			model.addAttribute("user", new Customer());

			return "resetPassword";
		}

		dataU.setPassword(user.getPassword());
		if(user.getUserName()!=null)
			dataU.setUserName(user.getUserName());

		model.addAttribute("MSG", "You have updated your details.");
		model.addAttribute("user", new Customer());
		return "login";
	}

	//Author: xu zhiye
	@GetMapping("/display/profile/{username}")
	public String displayProfile(Model model, @PathVariable String username) {
		Customer dataU = cusregister.searchUserByUserName(username);

		model.addAttribute("user", dataU);
		return "profilePage";
	}

	//Author: xu zhiye
	@PostMapping("/update/profile")
	public String updateProfile(Customer user, Model model) {
		// find by ID from database
		Customer dataU = cusregister.findById(user.getUserId());

		// ensure no duplicate userName
		if (!dataU.getUserName().equalsIgnoreCase(user.getUserName())
				&& cusregister.searchUserByUserName(user.getUserName()) != null) {
			model.addAttribute("MSG", "User Name exist, please try again!");
			model.addAttribute("user", new Customer());
			return "profilePage";
		} else {

			model.addAttribute("MSG", "You have updated your details.");
			return "homePage";
		}
	}
}
