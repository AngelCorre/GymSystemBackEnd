package com.angel.gym.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.angel.gym.Controllers.Dto.UserDto;
import com.angel.gym.Models.RoleEntity;
import com.angel.gym.Models.UserEntity;
import com.angel.gym.Repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	IUserRepository userRepository;

	@Override
	public UserEntity saveUser(UserDto userDto) {
		
	UserEntity user = UserEntity.builder()
				.name(userDto.getName())
				.email(userDto.getEmail())
				.password(userDto.getPassword())
				.benchPress(userDto.getBenchPress())
				.squat(userDto.getSquat())
				.deadlift(userDto.getDeadlift())
				.role(RoleEntity.builder()
						.id(userDto.getRoleDto().getId())
						.build())
				.isEnabled(userDto.isEnabled())
				.accountNoExpired(userDto.isAccountNoExpired())
				.accountNoLocked(userDto.isAccountNoLocked())
				.credentialNoExpired(userDto.isCredentialNoExpired())
				.build();

		return userRepository.save(user);
		
	}

	@Override
	public ResponseEntity<UserEntity> updateUser(Long id, UserDto detailsUser) {

		UserEntity user = userRepository.findById(id).orElse(null);
		
		user.setName(detailsUser.getName());
		user.setEmail(detailsUser.getEmail());
		user.setPassword(detailsUser.getPassword());
		user.setBenchPress(detailsUser.getBenchPress());
		user.setDeadlift(detailsUser.getDeadlift());
		user.setSquat(detailsUser.getSquat());
		user.setEnabled(detailsUser.isEnabled());
		user.setAccountNoExpired(detailsUser.isAccountNoExpired());
		user.setAccountNoLocked(detailsUser.isAccountNoLocked());
		user.setCredentialNoExpired(detailsUser.isCredentialNoExpired());

		UserEntity userR = userRepository.save(user);

		return ResponseEntity.ok(userR);

	}

	@Override
	public ResponseEntity<UserEntity> findUser(Long id) {

		UserEntity user = userRepository.findById(id).orElse(null);
		return ResponseEntity.ok(user);

	}

	@Override
	public List<UserEntity> findUsers() {

		return userRepository.findAll();

	}

	@Override
	public void DeleteUser(Long id) {

		userRepository.deleteById(id);
	}


}
