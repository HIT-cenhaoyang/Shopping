package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.service.AdminImplementation;


@Controller
public class AdminController {
	@Autowired
	private AdminInterface uservice;

	@Autowired
	public void setUserService(AdminImplementation userviceImpl) {
		this.uservice = userviceImpl;
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
			return "login";
		} else {
			if (dataU.getPassword().equals(user.getPassword())) {
				sessionObj.setAttribute("username", user.getUserName());
				return "redirect:/protected/list-admin";
			} else {
				model.addAttribute("errorMsg", "Your user name or password are wrong, please try again!");
				return "login";
			}
		}
	}
	
	@GetMapping("/protected/list-admin")
	public String listUsers(HttpSession sessionObj, Model model) {
		String username = (String) sessionObj.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		} else {
			return "homePage";
		}
	}
	
	@GetMapping("/logout")
	public String adminLogout(HttpSession sessionObj, Model model) {
		sessionObj.removeAttribute("username");
		return "redirect:/login";
	}
	
	
}
