/**
 * 
 */
package org.newsapp.api.security;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Shafiq
 *
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	private TokenService tokenService ;
	@Autowired
	private  ObjectMapper mapper;
	
	
	public LoginFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	


	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}


	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
		setAuthenticationSuccessHandler(this::onAuthenticationSuccess);
		setAuthenticationFailureHandler(this::onAuthenticationFailure);
	}

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws  IOException, ServletException {
		String usernameParam = req.getParameter("username");
		String passwordParam = req.getParameter("pass");
		Authentication auth = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(usernameParam, passwordParam));
		NewsUser user  = new NewsUser((User) auth.getPrincipal());
		return new UsernamePasswordAuthenticationToken(user, "",user.getAuthorities());
	}
	
	
	
	
	private  void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		HashMap<String, String> tokenResult = new HashMap<>();
		tokenResult.put("token", tokenService.createToken((NewsUser) authentication.getPrincipal()));
		mapper.writeValue(response.getOutputStream(), tokenResult);
	}
	
	private  void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		HashMap<String, String> tokenResult = new HashMap<>();
		tokenResult.put("message", "Please check username password");
		mapper.writeValue(response.getOutputStream(), tokenResult);
	}
	
	

}
