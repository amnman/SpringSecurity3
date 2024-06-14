package com.spring.security3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security3.entity.RoleEntity;


public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

}
