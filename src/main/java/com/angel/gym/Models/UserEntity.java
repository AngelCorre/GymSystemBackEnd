package com.angel.gym.Models;


import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 30, nullable = false, unique = true)
	private String name;

	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;

	@Column(name = "password", length = 50, nullable = false)
	private String password;

	@Column(name = "bench_press", nullable = false)
	private double benchPress;

	@Column(name = "deadlift", nullable = false)
	private double deadlift;

	@Column(name = "squat", nullable = false)
	private double squat;

	@ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_rol", nullable = true)
	private RoleEntity role;

	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled;
	
	@Column(name = "account_no_expired", nullable = false)
	private boolean accountNoExpired;
	
	@Column(name = "account_no_locked", nullable = false)
	private boolean accountNoLocked;
	
	@Column(name = "credential_no_expired", nullable = false)
	private boolean credentialNoExpired;

	
}





