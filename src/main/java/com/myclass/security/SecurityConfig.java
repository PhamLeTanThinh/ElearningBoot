package com.myclass.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDertailsService;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDertailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.antMatcher("/api/**") //những url bắt đầu bằng /api/ sẽ được kiểm tra
		.authorizeRequests()
		.antMatchers("/api/user/login")// nếu gặp url là /api/user/login 
		.permitAll() //thì sẽ bỏ qua phần kiểm tra đăng nhập
		.antMatchers("/api/role**")
		.hasAnyRole("ADMIN")
		.antMatchers("/api/user**")
		.hasAnyRole("ADMIN", "TEST")
		.anyRequest() //Tất cả những url còn lại thì 
		.authenticated(); //đều phải đăng nhập trước mới được phép truy cập
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), userDertailsService));
		//Cấu hình không lưu trữ bất kỳ thông tin nào vào session ( không sử dụng session)
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//ham` nay bo qua nhung file/trang ko can dang nhap
		web.ignoring()
		.antMatchers("/v2/api-docs",
		"/configuration/ui",
		"/swagger-resources/**",
		"/configuration/security",
		"/swagger-ui.html",
		"/webjars/**",
		"/upload/**");
	}
}
