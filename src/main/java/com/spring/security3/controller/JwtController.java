package com.spring.security3.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security3.model.JwtRequest;
import com.spring.security3.model.JwtResponse;
import com.spring.security3.model.UserModel;
import com.spring.security3.service.CustomUserDetailsService;
import com.spring.security3.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<UserModel> register(@RequestBody UserModel userModel){
		
		UserModel userModel1=customUserDetailsService.register(userModel);
		
		ResponseEntity<UserModel> re = new ResponseEntity<>(userModel1,HttpStatus.CREATED);
		
		return re;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
		
		//Authenticate User
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		
		UserDetails userDetails=customUserDetailsService.loadUserByUsername(jwtRequest.getUserName());
		
		//Generating Token
		
		String jwtToken=jwtUtil.generateToken(userDetails);
		
		JwtResponse jwtResponse =new JwtResponse(jwtToken);
		
		return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
	}
	
	@GetMapping("/current-user")
	public UserModel getCurrentUser(Principal principal) {
		UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(principal.getName());
		return (UserModel)userDetails;
	}

}
