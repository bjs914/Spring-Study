package com.bjs.webstore.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjs.webstore.domain.Product;
import com.bjs.webstore.domain.repository.ProductRepository;
import com.bjs.webstore.service.ProductService;

@Controller
@RequestMapping("market")	//이게 추가되면 url에서 /products : 접속불가, /market/products : 접속가능
public class ProductController {
//	@Autowired
//	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/products")
	public String list(Model model) {
/*
		Product iphone = new Product("P1234", "아이폰 6s", new BigDecimal(50_0000));
		iphone.setDescription("애플 아이폰 6s 스마트폰 " + "디스플레이 규격: 4.00-inch 640x1136, " + "후방 카메라: 8-megapixel");
		iphone.setCategory("Smartphone");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);
		model.addAttribute("product", iphone); // 모델에 추가
*/
//		model.addAttribute("products", productRepository.getAllProducts());	//직접 저장소에 데이터를 입력
		model.addAttribute("products", productService.getAllProducts());	//서비스를 통해서 저장소에 데이터를 입력하는 것
		
		return "products"; // 뷰 이름 반환
	}
	
	@RequestMapping("/update/stock")
	public String updateStock(Model model) {
		productService.updateAllStock();
//		return "redirect:/market/products";	//완료되면 다시 products 페이지를 반환(15번행이 없으면 이걸 쓰면됨)
		return "redirect:/market/products";	//완료되면 다시 market/products 페이지를 반환
//		15번행이 추가되면 위와 같이 리다이렉트 경로도 수정해줘야함
		//forward : task를 지정한 곳으로 보내는 것(외부에서, request를 요청한 곳에서는 알 수 없음)
	}
	
	@RequestMapping("/products/{category}") // 이렇게 하면 /market/products/laptop,tablet,등의 대소문자 구별없이 검색가능
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		model.addAttribute("products", productService.getProductsByCategory(productCategory));
		return "products";
	}

	@RequestMapping("/products/filter/{params}") // 6절 실습
	public String getProductsByFilter(@MatrixVariable(pathVar = "params") Map<String, List<String>> filterParams,
			Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
}
