package com.angel.gym.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.angel.gym.Controllers.Dto.UserDto;
import com.angel.gym.Models.UserEntity;

public interface IUserService {

	public UserEntity saveUser(UserDto user);

	public ResponseEntity<UserEntity> updateUser(Long id, UserDto detailsUser);

	public ResponseEntity<UserEntity> findUser(Long id);

	public List<UserEntity> findUsers();

	public void DeleteUser(Long id);


}
