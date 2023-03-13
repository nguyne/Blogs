package com.nguyen.blogs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyen.blogs.Config.JwtService;
import com.nguyen.blogs.Response.JwtAuthenticationResponse;
import com.nguyen.blogs.Response.LoginRequest;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:6666/")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication= authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			String jwt = jwtService.generateToken(loginRequest.getUsername());
			return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		}
		throw new UsernameNotFoundException("invalid user credentials");
	}
}
