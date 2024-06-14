package com.spring.security3.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello Spring Security!!";
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN','USER')")
		//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
		//@PreAuthorize("hasAnyAuthority('DELETE_AUTHORITY','UPDATE_AUTHORITY')")
}
