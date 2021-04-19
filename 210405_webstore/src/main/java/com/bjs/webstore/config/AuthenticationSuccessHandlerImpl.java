package com.bjs.webstore.config;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bjs.webstore.domain.User;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
	@Autowired
	HttpSession session; // autowiring session@Override

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String userName = "";
		if (authentication.getPrincipal() instanceof Principal) {
			userName = ((Principal) authentication.getPrincipal()).getName();
		} else {
			userName = ((User) authentication.getPrincipal()).getUsername();
		}
		session.setAttribute("userId", userName);
		response.sendRedirect(request.getContextPath() + "/market/customers");
	}
} 