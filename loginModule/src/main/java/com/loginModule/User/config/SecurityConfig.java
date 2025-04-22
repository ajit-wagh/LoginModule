package com.loginModule.User.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.loginModule.User.Service.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 

	@Autowired
	private JwtFilter authFilter; 

	// User Creation 
	@Bean
	public UserDetailsService userDetailsService() { 
		return new UserInfoService(); 
	} 

	// Configuring HttpSecurity 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
		return http.csrf(csrf->csrf.disable()).cors(cros->cros.disable())
				.authorizeHttpRequests(auth->auth.requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll())  
				.authorizeHttpRequests(auth->auth.requestMatchers("/auth/user/**").authenticated())
				.authorizeHttpRequests(auth->auth.requestMatchers("/auth/admin/**").authenticated())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/auth/welcome",
								"/auth/addNewUser",
								"/auth/generateToken",
								"/v3/api-docs/**",          // ✅ for Swagger
								"/swagger-ui/**",           // ✅ for Swagger
								"/swagger-ui.html"          // ✅ for Swagger
						).permitAll()
				)

				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  
				.authenticationProvider(authenticationProvider()) 
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) 
				.build(); 
	} 

	// Password Encoding 
	@Bean
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	} 

	@Bean
	public AuthenticationProvider authenticationProvider() { 
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
		authenticationProvider.setUserDetailsService(userDetailsService()); 
		authenticationProvider.setPasswordEncoder(passwordEncoder()); 
		return authenticationProvider; 
	} 

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
		return config.getAuthenticationManager(); 
	} 


} 
