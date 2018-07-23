/**
 * 
 */
package org.newsapp.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Shafiq
 *
 */
public class AuthenticationTokenService {
	
	public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	
	public void addAuthentication(HttpServletRequest request, HttpServletResponse response, UsernamePasswordAuthenticationToken authentication) {
		 final UserDetails user = (UserDetails) authentication.getPrincipal();
		 
	}
}
