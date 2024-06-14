package com.spring.security3.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security3.service.CustomUserDetailsService;
import com.spring.security3.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Call this Filter only once per request
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	CustomUserDetailsService customDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws  IOException, ServletException {
		// Get JWT Token from request header and validate the JWT token.
		
		String bearerToken=request.getHeader("Authorization");
		String userName=null;
		String token=null;
		
		//check if token exist or has Bearer text
		
		if(bearerToken!=null && bearerToken.startsWith("Bearer")) {
			
			//extract JWT Token from bearer token
			token = bearerToken.substring(7);
			
			try {
				//Extract Username from token
				userName=jwtUtil.extractUsername(token);
				
				//Get UserDetails for this User
				UserDetails userDetails=customDetailsService.loadUserByUsername(userName);
				
				//Security Checks
				if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
					
					UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(userName,null, userDetails.getAuthorities());
					upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(upat);
					
				}else {
					System.out.println("Invalid Bearer Token Format!");
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Invalid Bearer Token Format!");
		}
		
		filterChain.doFilter(request, response);
	}
}
