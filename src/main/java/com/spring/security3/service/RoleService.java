package com.spring.security3.service;

import java.util.List;

import com.spring.security3.model.RoleModel;

public interface RoleService {
	
	public RoleModel createRole(RoleModel roleModel);
	public List<RoleModel> getAllRoles();
	public RoleModel getRoleByID(long id);
	public void deleteRoleByID(long id);
}
