package com.bjs.webstore.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;
import org.springframework.web.util.UrlPathHelper;

import com.bjs.webstore.domain.Product;
import com.bjs.webstore.interceptor.ProcessingTimeLogInterceptor;
import com.bjs.webstore.interceptor.PromoCodeInterceptor;
import com.bjs.webstore.validator.ProductImageValidator;
import com.bjs.webstore.validator.ProductValidator;
import com.bjs.webstore.validator.UnitsInStockValidator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration	//Bean설정을 알려주는 어노테이션, 클래스가 빈 메소드를 포함
@EnableWebMvc	//구성정보를 수입
@ComponentScan("com.bjs.webstore")	//빈으로 등록되기 위한 클래스들을 자동으로 IoC(Inversion of Control)컨테이너에서 등록/검색, Configuration과 같이 쓰임
//basePackages , basePackageClasses 를 통해 클래스, 패키지를 찾도록 할 수 있다. (ex) @ComponentScan(basePackages = "com.bjs.webstore"); 
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter{
	//WebMvcConfigurerAdapter : concrete클래스,,
	//dispatcher : 톰캣이 만든다
	
	@Override
	public void configureDefaultServletHandling(
	DefaultServletHandlerConfigurer configurer) {
	configurer.enable();
	//configureDefaultServletHandling(): 디폴트 서블릿 핸들러를 위한 설정 담당
	}
	
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		//InternalResourceViewResolver : view에 대한 방법을 결정해줌	
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override // 행열변수 지원하게 설정
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);	//세미콜론도 넘겨주라는 의미임
		configurer.setUrlPathHelper(urlPathHelper);
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("messages");
		resource.setDefaultEncoding("utf-8");
		return resource;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/images/");	//이미지 불러오기와 관련됨
		registry.addResourceHandler("/pdf/**").addResourceLocations("/resources/pdf/");	//파일 불러오기와 관련됨(pdf형식)
	}
//	210409추가
	@Bean
	public MappingJackson2JsonView jsonView() {
	MappingJackson2JsonView jsonView = new
	MappingJackson2JsonView();
	jsonView.setPrettyPrint(true);
	return jsonView;
	}

	@Bean
	public MarshallingView xmlView() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Product.class);
		MarshallingView xmlView = new MarshallingView(marshaller);
		return xmlView;
	}
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		ArrayList<View> views = new ArrayList<View>();
		views.add(jsonView());
		views.add(xmlView());
		resolver.setDefaultViews(views);
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ProcessingTimeLogInterceptor());
		registry.addInterceptor(new ProcessingTimeLogInterceptor());
		LocaleChangeInterceptor localeChangeInterceptor =
		new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(promoCodeInterceptor())
		.addPathPatterns("/**/market/products/specialOffer");
		//addPathPatterns을 사용해서 promocode를 선택적으로 차단?사용했다
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("ko"));
		return resolver;
	}
	
	@Bean
	public HandlerInterceptor promoCodeInterceptor() {
		PromoCodeInterceptor promoCodeInterceptor = new PromoCodeInterceptor();
		promoCodeInterceptor.setPromoCode("OFF3R");
		promoCodeInterceptor.setOfferRedirect("market/products");
		promoCodeInterceptor.setErrorRedirect("invalidPromoCode");
		return promoCodeInterceptor;
	}
//	210409추가 종료
//	210413 추가
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
//	@Bean
//	public ObjectMapper getJacksonObjectMapper() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.findAndRegisterModules();
//		objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		return objectMapper;
//	}
	
	@Override
	public Validator getValidator() {
		return validator();
	}//반환 값의 자료형: org.springframework.validation.Validator
//	210413 추가 종료
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}
	
//	210414 추가 시작
	@Bean
	public ProductValidator productValidator() {
		Set<Validator> springValidators = new HashSet<Validator>();
		springValidators.add(new UnitsInStockValidator());
		springValidators.add(productImageValidator());	//이미지크기,용량관련
		springValidators.add(new ProductImageValidator());
		ProductValidator productValidator = new ProductValidator();
		productValidator.setSpringValidators(springValidators);
		return productValidator;
	}
	
	@Bean	//이미지 용량에 따른 제한
	public ProductImageValidator productImageValidator() {
		ProductImageValidator productImageValidator = new ProductImageValidator();
		productImageValidator.setAllowedSize(1024 * 1024);
		return productImageValidator;
	}
//	210414 추가 종료
}
