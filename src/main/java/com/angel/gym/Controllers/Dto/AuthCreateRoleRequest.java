package com.angel.gym.Controllers.Dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public record AuthCreateRoleRequest(@Size(max = 3, message = "The user can't have more than 3 roles") String roleName) {

}
