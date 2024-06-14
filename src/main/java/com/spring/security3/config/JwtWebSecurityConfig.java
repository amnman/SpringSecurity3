package com.spring.security3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.security3.filter.JwtAuthenticationFilter;
import com.spring.security3.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)  //method level authorization
public class JwtWebSecurityConfig{
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	JwtAuthenticationFilter jwtFilter;
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthEntryPoint;

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf->csrf.disable())
		    .cors(cors->cors.disable())
		    .authorizeHttpRequests(rq->{
		    	rq.requestMatchers("/api/login","/api/register","/api/roles","/h2-console/**","/favicon.ico").permitAll().anyRequest().authenticated();
		    	})
		    .exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthEntryPoint))
		    .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.headers(h->h.frameOptions(f->f.disable()));
		
		http.authenticationProvider(authenticationProvider());
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
