package com.angel.gym.Repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.gym.Models.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{
	
	public Optional<UserEntity> findUserEntityByName(String username);


}
