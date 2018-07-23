/**
 * 
 */
package org.newsapp.api.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Shafiq
 *
 */

public class AuthFilter extends GenericFilterBean {
	private static String HEADER_NAME = "X-AUTH-TOKEN";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		doFilterInternal(request, response, chain);
	}

	private void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = getTokenHeader(request);
		if (token == null) {
			sendAccessDeninedMessage(response);
		} else {
			try {
				NewsUser user = tokenService.parseToken(token);
				if (user != null) {
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()));
					chain.doFilter(request, response);
				} else {
					sendAccessDeninedMessage(response);
				}
			} catch (Exception e) {
				e.getStackTrace();
				sendAccessDeninedMessage(response);
			}
		}
	}

	private void sendAccessDeninedMessage(HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		HashMap<String, String> messageData = new HashMap<>();
		messageData.put("message", "Token is Inavalid");
		mapper.writeValue(response.getOutputStream(), messageData);
	}

	public String getTokenHeader(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		String token = null;
		while (headers.hasMoreElements()) {
			String providedHeaderKey = headers.nextElement();
			if (providedHeaderKey.equalsIgnoreCase(HEADER_NAME)) {
				token = request.getHeader(providedHeaderKey);
				break;
			}
		}
		return token;
	}

}
