package com.bjs.webstore.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//@Controller, RequestMapping은 함꼐 다닌다,+Model은 Requestmapping에 있다
@Controller
@RequestMapping("/")	//이걸 붙이게 되면 /뒤에 아무것도 입력하지않고 welcome.jsp에 접속가능
public class HomeController {
//	@RequestMapping("/welcome")	//기존
	@RequestMapping	
//클래스위에 RequestMapping("/")을 적용시키기 위해 선언, 12번행처럼 하게 되면 클래스밖의 RequestMapping("/test")등으로 변경해야함 
	public String welcome(Model model, RedirectAttributes redAttrs,
			Principal principal) {
//		String greeting = "FLASH 속성방법입니다!";	//flash속성을 활용하기 위한 것
//		String tagline = "세상에서 하나 뿐인 웹 가게";
		
		model.addAttribute("greeting", "환영합니다!");
		model.addAttribute("tagline", "세상에서 하나 뿐인 웹 가게");
//		redAttrs.addFlashAttribute("greeting", greeting);	//flash 속성을 활용하기 위한 것
//		redAttrs.addFlashAttribute("tagline", tagline);
		  if (principal != null) {
		    	String username = principal.getName();
		    	model.addAttribute("username", principal.getName());
		    }
		return "welcome";
//		 return "forward:/welcome/greeting";	//결과 : 환영합니다!, 더좋은학원이 나오게됨
//		 return "redirect:/welcome/greeting"; // 결과 : 환영합니다!라는 문구가 사라지고, 더좋은학원만 나옴
	}
	
	@RequestMapping("/welcome/greeting") //forward방법 예시
	public String greeting(Model model) {
		model.addAttribute("tagline", "더좋은학원");
		return "welcome";
	}
	
//	@RequestMapping("/welcome/greeting") 
//	public String greeting() {
//		return "welcome";	//flash속성 활용
//	}
}