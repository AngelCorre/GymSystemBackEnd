package com.angel.gym.Filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.angel.gym.Util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public class JwtTokenValidator extends OncePerRequestFilter{
	
	
	private JwtUtils jwtUtils;
	

	public JwtTokenValidator(JwtUtils jwtUtils) {
		super();
		this.jwtUtils = jwtUtils;
	}



	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (jwtToken != null) {
			
			jwtToken = jwtToken.substring(7);
			
			DecodedJWT decoded = jwtUtils.validateToken(jwtToken);
			
			String username = jwtUtils.extractUsername(decoded);
			String stringAuthorities = jwtUtils.getSpecificClaim(decoded, "authorities").asString();
			
			Collection <? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
	
			SecurityContext context = SecurityContextHolder.getContext();
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
			
			context.setAuthentication(authentication);
			
			SecurityContextHolder.setContext(context);
			
		}
		
		filterChain.doFilter(request, response);
		
	}

}
