package com.bjs.webstore.config;

import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.util.UrlPathHelper;

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
	public InternalResourceViewResolver	getInternalResourceViewResolver() {
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

}
