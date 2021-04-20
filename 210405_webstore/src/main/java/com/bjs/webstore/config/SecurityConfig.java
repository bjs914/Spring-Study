package com.bjs.webstore.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.bjs.webstore.domain.UserWs;
import com.bjs.webstore.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		List<UserWs> users = userService.getAllUsers();
		for (UserWs u : users) {
			if ("admin".equals(u.getUsername())) {//여기에서 u.getUsername에 DB에 있는 고객의 이름이 담기게 됨
				auth.inMemoryAuthentication().withUser(u.getUsername()).password(u.getPassword()).roles("USER",
						"ADMIN");	//여기에서 admin이라는게 저장되어있는 데이터가 admin이라는 String과 같으면 roles의 안에 지정된 이름으로 권한을 부여함
			} else if("custrep".equals(u.getUsername())){
				auth.inMemoryAuthentication().withUser("custrep").password("1234").roles("USER","SERVICE");
				//admin이 아닌 아이디를 입력하게 되면 단순히 user라는 이름을 가진 역할을 지정,user = product목록을 볼수있는권한, service = customer를 볼 수 있는 권한을 가짐
			}else {
				auth.inMemoryAuthentication().withUser(u.getUsername()).password(u.getPassword()).roles("USER");
				//admin이 아닌 아이디를 입력하게 되면 단순히 user라는 이름을 가진 역할을 지정
			}
		}
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin().loginPage("/login").usernameParameter("userId").passwordParameter("password");
		//formLogin() : login이라는 기본값이 특정되지 않으면, 생성하고 생성을 위해서는 lonin페이지(jsp)를 만들어야 해당 경로를 거쳐 인증을 준다.
//		httpSecurity.formLogin().defaultSuccessUrl("/").failureUrl("/login?error");//로그인이 성공하면 market/products/add 경로반환, 아니면 /login?error경로 반환
		httpSecurity.formLogin().failureUrl("/login?error");	//기존(교안내용)
		httpSecurity.logout().logoutSuccessUrl("/login?logout");	//logout 성공시, /login?logout 경로 반환
		httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");	//로그인 실패시, 해당 경로 반환
															// ? 오른쪽 : parameta
		httpSecurity.formLogin().successHandler(authenticationSuccessHandler)
		.defaultSuccessUrl("/market/customers").failureUrl("/login?error");
		httpSecurity.authorizeRequests().antMatchers("/").permitAll()
//		.antMatchers("/**/add").access("hasRole('ADMIN')")
		.antMatchers("/**/products/add").access("hasRole('ADMIN')")
		.antMatchers("/**/customers/add").access("hasRole('SERVICE')")
		.antMatchers("/**/market/**").access("hasRole('USER')");	//url끝이 add로 끝나면 권한을 admin에게 주고, market이라는 url이 중간에 있으면 일반 유저에게 권한을 준다는 의미
		httpSecurity.csrf().disable();
	}
}