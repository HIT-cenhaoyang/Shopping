package sg.nus.edu.shopping.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//Author: Xu Zhiye
@Component
public class SessionCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect("/login");
			return false;
		}

		return true;
	}

}
