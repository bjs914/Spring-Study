package com.bjs.webstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@RequestMapping
	public String get(HttpServletRequest request) {
		return "redirect:/cart/" + request.getSession(true).getId();
		//세션이 없으면 만들어서(true) getId에 붙여서 cart/다음에 붙여서 리다이렉트함
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public String getCart(@PathVariable(value = "cartId") String cartId, Model model) {
		model.addAttribute("cartId", cartId);
		return "cart";
	}
}
