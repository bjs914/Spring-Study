package com.bjs.webstore.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends
	AbstractAnnotationConfigDispatcherServletInitializer {
	// AACDSInitializer
	
	@Override	//rootapplication context에 대한 것(web.xml에서)
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {	//db에 대한 설정이 저장된 클래스를 연결
				RootApplicationContextConfig.class };
	} 
	
	@Override
	protected Class<?>[] getServletConfigClasses() {//배정기 서블릿 appl’ context 에 대한 것(web.xml에서)
	// 콘트롤러 클래스와 뷰 파일을 알려줌
	return new Class[] { WebApplicationContextConfig.class };
	}
	
	@Override
	protected String[] getServletMappings() {	//배정기 서블릿의 서블릿 매핑을 지정(web.xml에서)
	return new String[] { "/" };
	}
	
	@Override	//한글인코딩방법
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        return new Filter[] { characterEncodingFilter };
    }
	
	@Override//210419추가-11장마지막
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(new RequestContextListener());
	}
}
