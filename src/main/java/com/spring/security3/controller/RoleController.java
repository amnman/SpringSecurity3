package com.spring.security3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security3.model.RoleModel;
import com.spring.security3.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@PostMapping("/roles")
	public RoleModel createRole(@RequestBody RoleModel roleModel) {
		return roleService.createRole(roleModel);
	}
	
	@GetMapping("/roles")
	public List<RoleModel> getAllRoles(){
		return roleService.getAllRoles();
	}

	@DeleteMapping("/roles/{roleId}")
	public void deleteRole(@PathVariable Long roleId) {
		roleService.deleteRoleByID(roleId);
	}
	
}
