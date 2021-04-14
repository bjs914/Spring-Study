package com.bjs.webstore.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bjs.webstore.domain.Product;
import com.bjs.webstore.exception.NoProductsFoundUnderCategoryException;
import com.bjs.webstore.exception.ProductNotFoundException;
import com.bjs.webstore.service.ProductService;
import com.bjs.webstore.validator.ProductValidator;
import com.bjs.webstore.validator.UnitsInStockValidator;

@Controller
@RequestMapping("market")	//이게 추가되면 url에서 /products : 접속불가, /market/products : 접속가능
public class ProductController {
//	@Autowired
//	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductValidator productValidator;
	
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
	public String getProductsByCategory(Model model, @PathVariable("category") String category) {
		List<Product> products =
				productService.getProductsByCategory(category);
		
		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
			} model.addAttribute("products", products);
		return "products";
	}
	
	@RequestMapping("/products/filter/{params}") // 6절 실습
	public String getProductsByFilter(@MatrixVariable(pathVar = "params") Map<String, List<String>> filterParams,
			Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product") // 7절 실습
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}
	
	@RequestMapping(value = "/products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	@RequestMapping(value = "/products/add", method = RequestMethod.POST) 
//			produces = MediaType.APPLICATION_JSON_VALUE+ "; charset=utf-8")
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, BindingResult result,
			Model model, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "addProduct";
		}
		try {
		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException(
					"허용되지 않은 항목을 엮어오려고함: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		} else {
			/**
			* 상품 영상 메모리 내용 정한 폴더에 파일로 보관
			*/
			MultipartFile productImage = newProduct.getProductImage();
			String rootDirectory = request.getSession().getServletContext().getRealPath("/");
			if (productImage != null && !productImage.isEmpty()) {
				try {
					productImage.transferTo(
							new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".png"));
				} catch (Exception e) {
					throw new RuntimeException("Product Image saving failed", e);
				}
			}
			/**
			 * 상품 설명서 메모리 내용 정한 폴더에 파일로 보관
			 */
			MultipartFile productManual = newProduct.getProductManual();
			if (productManual != null && !productManual.isEmpty()) {
				try {
					productManual.transferTo(
							new File(rootDirectory + "resources\\pdf\\" + newProduct.getProductId() + ".pdf"));
				} catch (Exception e) {
					throw new RuntimeException("상품 설명서 저장 실패", e);
				}
			}
			productService.addProduct(newProduct);
		}
		return "redirect:/market/products";
		} catch (DataAccessException e) {
			String msg = e.getMessage();
			int idx = msg.lastIndexOf("Duplicate");
			model.addAttribute("errorMsg", msg.substring(idx));
			return "addProduct";
		}
	}
	
	@ExceptionHandler(ProductNotFoundException.class)	//HandleError담당
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("productNotFound");	//뷰.jsp파일이름을 의미함
		return mav;
	}
	
	@RequestMapping("/products/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("productId", "name", "unit*", "description", "manufacturer", "category", "condition",
				"productImage", "productManual");
		binder.setValidator(productValidator);
	}
}
