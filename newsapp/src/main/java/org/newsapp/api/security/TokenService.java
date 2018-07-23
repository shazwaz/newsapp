/**
 * 
 */
package org.newsapp.api.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Shafiq
 *
 */
@Service
public class TokenService {

	private String secret = "MyScreteToken";
	private int expirayDelay = 100;
	private static final String SEPARATOR = ",";

	public String createToken(NewsUser user) {
		return Jwts.builder().setClaims(buildClaim(user)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public NewsUser parseToken(String token) {
		NewsUser user = null;
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			user = parseClaims(claims);
		} catch (Exception ex) {
			throw new RuntimeException("Token is not valid");
		}
		return user;
	}

	private Claims buildClaim(NewsUser user) {
		Claims claims = Jwts.claims();
		claims.setSubject(user.getUsername());
		claims.setExpiration(getExpirationDate());
		addRoles(claims, user.getAuthorities());
		return claims;
	}

	private NewsUser parseClaims(Claims claims) {
		NewsUser user = null;
		if (claims != null) {
			String username = claims.getSubject();
			String roles = (String) claims.get("roles");
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			String[] splitedRoles = roles.split(SEPARATOR);
			for (String role : splitedRoles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
			user = new NewsUser(username,"", authorities);
		}
		return user;
	}

	private void addRoles(Claims claims, Collection<? extends GrantedAuthority> authoriteis) {
		if (authoriteis != null && !authoriteis.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (GrantedAuthority authority : authoriteis) {
				if (builder.length() > 0) {
					builder.append(SEPARATOR);
				}
				builder.append(authority.getAuthority());
			}
			claims.put("roles", builder.toString());
		}
	}

	private Date getExpirationDate() {
		Calendar calc = Calendar.getInstance();
		calc.add(Calendar.MINUTE, expirayDelay);
		return calc.getTime();

	}

}
