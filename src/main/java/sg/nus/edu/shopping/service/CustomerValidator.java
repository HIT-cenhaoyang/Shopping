package sg.nus.edu.shopping.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.edu.shopping.model.Customer;


@Component
public class CustomerValidator implements Validator {

	//Author: xu zhiye
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Customer.class.isAssignableFrom(clazz);
	}

	//Author: xu zhiye
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "password is required.");
		Customer user = (Customer) obj;
		String password = user.getPassword();
		boolean result = false;

		for (int i = 0; i < password.length(); i++) {
			char a = password.charAt(i);
			if (a >= 'A' && a <= 'Z') {
				result = true;
				break;
			}
		}
		if (result == false) {
			errors.rejectValue("password", "error.password", "password must contain at least one upper case");
		}
		
		boolean number = false;
		for (int i = 0; i < password.length(); i++) {
			char a = password.charAt(i);
			if (a >= '0' && a <= '9') {
				number = true;
				break;
			}
		}
		if (number == false) {
			errors.rejectValue("password", "error.password", "password must contain at least one number");
		}

	}
}