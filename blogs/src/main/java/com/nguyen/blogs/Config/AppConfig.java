package com.nguyen.blogs.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.nguyen.blogs.security.LibraryUserDetailsService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AppConfig {
	@Autowired
	private LibraryUserDetailsService userDetailsService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtBlacklist jwtBlacklist;


	
	private static final String[] SECURED_URLs = {"/api/v1/**"};
	private static final String[] UN_SECURED_URLs = {
			"/api/v1/auth/**",
			"/api/v1/user/**", 
			"/api/v1/post/user/**",
			"/api/v1/post",
			"/api/v1/post/search/**",
			"/api/v1/post/{post_id}/comments"
			};
	
	private static final String [] AUTHORITY_ADMIN_USER = {"USER","ADMIN"};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers(UN_SECURED_URLs).permitAll()
				.and().authorizeHttpRequests()
				.requestMatchers(SECURED_URLs).hasAnyAuthority(AUTHORITY_ADMIN_USER)
				.anyRequest().authenticated()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authenticationProvider(authenticationProvider)
				.addFilterBefore(new JwtAuthenticationFilter(jwtBlacklist, jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
				.logout().logoutUrl("/api/v1/auth/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						String token = request.getHeader("Authorization");
						if (token != null && token.startsWith("Bearer ")) {
							token = token.substring(7);
							jwtBlacklist.addToBlacklist(token);
						}
						response.setStatus(HttpStatus.OK.value());
					}
				});
		 return http.build();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
