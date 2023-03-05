package com.avakhilkumar.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestToken = request.getHeader("Autherization");
		
		String username = null;
		
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {
			
			token = requestToken.substring(7);
			
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token.");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired.");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT Token.");
			}
			
		} else {
			System.out.println("JWT Token does not begin with Bearer.");
		}
		
//		Validate then token once we get it:
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
			
			Boolean validatedToken = this.jwtTokenHelper.validateToken(token, userDetails);
		
			if(validatedToken) {
//				If the token validates then set authentication by creating UsernamePasswordAuthenticationToken object:
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			
			} else {
				System.out.println("Invalid JWT Token.");
			}
	
		} else {
			System.out.println("Username is null or Context is not null.");
		}
		
		filterChain.doFilter(request, response);
	}
	
}
