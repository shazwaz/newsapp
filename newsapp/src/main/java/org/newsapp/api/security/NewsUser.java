/**
 * 
 */
package org.newsapp.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Shafiq
 *
 */
public class NewsUser extends  User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public NewsUser(User user) {
		this(user.getUsername(),"",user.getAuthorities());
	}
	
	public NewsUser(String username,String passWord,Collection<? extends GrantedAuthority> authorities) {
		super(username, passWord, authorities);
		
	}
}
