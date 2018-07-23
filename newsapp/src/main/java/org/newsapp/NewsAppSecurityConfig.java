/**
 * 
 */
package org.newsapp;

import org.newsapp.api.security.AuthFilter;
import org.newsapp.api.security.LoginFilter;
import org.newsapp.api.security.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Shafiq
 */
@Configuration
public class NewsAppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	LoginFilter loginFilter() throws Exception {
		LoginFilter filter  = new LoginFilter(new AntPathRequestMatcher("/api/auth/login"));
		filter.setTokenService(tokenService());
		filter.setAuthenticationManager(authenticationManager());
		System.out.println("#####################"+authenticationManager());
		return filter;
	}
	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}
	
	@Bean
	public TokenService tokenService() {
		return new TokenService();
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		 auth.inMemoryAuthentication().withUser("shafiq")
		 .password("shafiq")
		 .roles("ADMIN")
		 .accountExpired(false).accountLocked(false).authorities("ADMIN");
		 
		
	}
	
	
	 
	
}
