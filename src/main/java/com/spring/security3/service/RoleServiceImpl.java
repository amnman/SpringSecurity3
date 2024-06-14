package com.spring.security3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.security3.entity.RoleEntity;
import com.spring.security3.model.RoleModel;
import com.spring.security3.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleModel createRole(RoleModel roleModel) {
		RoleEntity roleEntity1=new RoleEntity();
		BeanUtils.copyProperties(roleModel, roleEntity1);
		RoleEntity roleEntity2=roleRepository.save(roleEntity1);
		BeanUtils.copyProperties(roleEntity2, roleModel);
		return roleModel;
	}
	
	@Override
	public List<RoleModel> getAllRoles() {
		// TODO Auto-generated method stub
		List<RoleEntity> roleEntities=roleRepository.findAll();
		List<RoleModel> roleModels=new ArrayList<>();
		RoleModel roleModel=null;
		for(RoleEntity roleEntity:roleEntities) {
			roleModel=new RoleModel();
			BeanUtils.copyProperties(roleEntity, roleModel);
			roleModels.add(roleModel);
		}
		return roleModels;
	}

	@Override
	public RoleModel getRoleByID(long id) {
		// TODO Auto-generated method stub
		RoleEntity roleEntity= roleRepository.findById(id).get();
		RoleModel roleModel =new RoleModel();
		BeanUtils.copyProperties(roleEntity, roleModel);
		return roleModel;
	}

	@Override
	public void deleteRoleByID(long id) {
		// TODO Auto-generated method stub
		roleRepository.deleteById(id);
	}
	
}
