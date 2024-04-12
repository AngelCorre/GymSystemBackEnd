package com.angel.gym.Controllers.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank String username, @NotBlank String email,@NotBlank String password, double benchpress, double squat, double deadlift) {

}
