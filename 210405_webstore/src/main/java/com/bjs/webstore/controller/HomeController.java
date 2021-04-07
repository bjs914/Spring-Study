package com.bjs.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


//@Controller, RequestMapping은 함꼐 다닌다,+Model은 Requestmapping에 있다
@Controller
@RequestMapping("/")	//이걸 붙이게 되면 /뒤에 아무것도 입력하지않고 welcome.jsp에 접속가능
public class HomeController {
//	@RequestMapping("/welcome")	//기존
	@RequestMapping	
//클래스위에 RequestMapping("/")을 적용시키기 위해 선언, 12번행처럼 하게 되면 클래스밖의 RequestMapping("/test")등으로 변경해야함 
	public String welcome(Model model) {
		model.addAttribute("greeting", "환영합니다!");
		model.addAttribute("tagline", "세상에서 하나 뿐인 웹 가게");
		return "welcome";
	}
}