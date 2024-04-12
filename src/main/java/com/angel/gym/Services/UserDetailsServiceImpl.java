package com.angel.gym.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.angel.gym.Controllers.Dto.AuthCreateUser;
import com.angel.gym.Controllers.Dto.AuthLoginRequest;
import com.angel.gym.Controllers.Dto.AuthResponse;
import com.angel.gym.Models.RoleEntity;
import com.angel.gym.Models.UserEntity;
import com.angel.gym.Repositories.IRoleRepository;
import com.angel.gym.Repositories.IUserRepository;
import com.angel.gym.Util.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IUserRepository iUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = iUserRepository.findUserEntityByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_".concat(userEntity.getRole().getName())));
		
		userEntity.getRole().getPermissions().stream()
		.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
		
		
		
		
		return new User(userEntity.getName(),
				userEntity.getPassword(),
				userEntity.isEnabled(),
				userEntity.isAccountNoExpired(),
				userEntity.isAccountNoLocked(),
				userEntity.isCredentialNoExpired(),
				authorities);
	}
	
	public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
		
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();
		
		Authentication authentication = this.authenticate(username, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(username, "user loged  successfuly", accessToken, true);
		
		return authResponse;
		
	}
	
	public Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = this.loadUserByUsername(username);
		
		if (userDetails == null) {
			
			throw new BadCredentialsException("Invaid username or password");
			
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			
			throw new BadCredentialsException("Invalid password");
			
		}
		
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
		
		
	}
	
	public AuthResponse createUser(AuthCreateUser authCreateUser) {
		
		String username = authCreateUser.username();
		String email = authCreateUser.email();
		String password = authCreateUser.password();
		double benchPress = authCreateUser.benchpress();
		double squat = authCreateUser.squat();
		double deadlift = authCreateUser.deadlift();
		
		/*RoleEntity roleEntitySet = (RoleEntity) roleRepository.findRoleEntityByName(roleRequest).stream();
		
		if (((CharSequence) roleEntitySet).isEmpty()) {
			
			throw new IllegalArgumentException("The specified roles doesn't exist.");
			
		}*/
		
		UserEntity userEntity = new UserEntity().builder()
				.name(username)
				.email(email)
				.password(passwordEncoder.encode(password))
				.benchPress(benchPress)
				.squat(squat)
				.deadlift(deadlift)
				.role(RoleEntity.builder()
						.id(2L)
						.build())
				.isEnabled(true)
				.accountNoLocked(true)
				.accountNoExpired(true)
				.credentialNoExpired(true)
				.build();
		
		UserEntity userCreated = iUserRepository.save(userEntity);
		
		List<SimpleGrantedAuthority> autorityList = new ArrayList<>();
		
		autorityList.add(new SimpleGrantedAuthority("ROLE_User"));
		
		autorityList.add(new SimpleGrantedAuthority("READ"));
	
		SecurityContext context = SecurityContextHolder.getContext();
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getName(), userCreated.getPassword(), autorityList);
		
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(userCreated.getName(), "User created successfully", accessToken, true);
		
		return authResponse;
		
	}

}
