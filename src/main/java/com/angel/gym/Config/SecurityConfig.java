package com.angel.gym.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.angel.gym.Filter.JwtTokenValidator;
import com.angel.gym.Services.UserDetailsServiceImpl;
import com.angel.gym.Util.JwtUtils;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                .authorizeHttpRequests(http -> {

                    //ENDPOINTS PUBLICOS
                	http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                	http.requestMatchers(HttpMethod.GET, "/api/v1/user/users").permitAll();
                	http.requestMatchers(HttpMethod.GET, "/api/v1/user/user/{id}").permitAll();
                	
                    //ENDPOINTS PRIVADOS
                    http.requestMatchers(HttpMethod.POST, "/api/v1/user/user").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/user/user/{id}").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/user/user/{id}").hasAuthority("DELETE");

                    //ENDPOINTS NO ESPECIFICADOS
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
				
	}
	
	/*@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/login")
                        .maximumSessions(1)
                        .expiredUrl("/login")
                        .sessionRegistry(sessionRegistry())
                        )
                .sessionManagement(management -> management
                        .sessionFixation()
                        .migrateSession()
                        
                        )
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                        .build();
				
	}*/
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return  authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(passwordEncoder());
		
		provider.setUserDetailsService(userDetailsServiceImpl);
		
		return provider;
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
		
	}
	
	
}
