package com.spring.security3.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security3.entity.RoleEntity;
import com.spring.security3.entity.UserEntity;
import com.spring.security3.model.RoleModel;
import com.spring.security3.model.UserModel;
import com.spring.security3.repository.RoleRepository;
import com.spring.security3.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	//This Method Does Validation for User Existence
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity=userRepository.findByUsername(username);
		if(userEntity==null) {
			throw new UsernameNotFoundException("User Not Found!!");
		}
		
		UserModel userModel = new UserModel();
		
		BeanUtils.copyProperties(userEntity, userModel);
		
		Set<RoleModel> roleModels = new HashSet<>();
		RoleModel rm=null;
		for(RoleEntity re:userEntity.getRoles()) {
			rm=new RoleModel();
			rm.setRoleName(re.getRoleName());
			rm.setId(re.getId());
			roleModels.add(rm);
		}
		
		userModel.setRoles(roleModels);
		
		return userModel;
	}
	
	public UserModel register(UserModel userModel) {
		
		UserEntity userEntity=new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity); //doesnt do a deep copy
		
		//fetch every role from DB based on role ID and set this role to user entity
		
		Set<RoleEntity> roleEntities=new HashSet<>();
		
		for(RoleModel rm : userModel.getRoles()) {
			Optional<RoleEntity> optRole=roleRepository.findById(rm.getId());
			if(optRole.isPresent()) {
				roleEntities.add(optRole.get());
			}
		}
		
		userEntity.setRoles(roleEntities);
		
		userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
		
		userEntity= userRepository.save(userEntity);
		
		BeanUtils.copyProperties(userEntity, userModel);
		
		//convert role entity to role model
		
		Set<RoleModel> roleModels = new HashSet<>();
		RoleModel rm=null;
		for(RoleEntity re:userEntity.getRoles()) {
			rm=new RoleModel();
			rm.setRoleName(re.getRoleName());
			rm.setId(re.getId());
			roleModels.add(rm);
		}
		
		userModel.setRoles(roleModels);
		
		return userModel;
	}

}
