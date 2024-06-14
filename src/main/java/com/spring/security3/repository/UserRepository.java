package com.spring.security3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security3.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	public UserEntity findByUsername(String username);

}
