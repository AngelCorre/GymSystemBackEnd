package com.angel.gym.Controllers.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private double benchPress;
	
	@NotNull
	private double squat;
	
	@NotNull
	private double deadlift;
	
	private RoleDto roleDto;
	
	@NotNull
	private boolean isEnabled;
	
	@NotNull
	private boolean accountNoExpired;
	
	@NotNull
	private boolean accountNoLocked;
	
	@NotNull
	private boolean credentialNoExpired;

}
