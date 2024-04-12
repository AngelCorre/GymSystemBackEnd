package com.angel.gym.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.angel.gym.Models.RoleEntity;

@Repository
public interface IRoleRepository extends CrudRepository<RoleEntity, Long>{
	
	List<RoleEntity> findRoleEntityByName(String roleName);
	
}
