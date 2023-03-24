package com.nguyen.blogs.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Response.ApiResponse;
import com.nguyen.blogs.Service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:6666/") // để tránh lỗi CROSS
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private UserService uService;

	
	@GetMapping("/{username}")
	public ResponseEntity<?> getListUserByUsername(@PathVariable(value = "username") String username) {
			User users = uService.findByuserName(username);
			return ResponseEntity.ok(users);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getListUser() {
			List<User> users = uService.getListUsers();
			return ResponseEntity.ok(users);
	}

	@PostMapping("")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User newUser = uService.createUsers(user);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User newUser,
			@PathVariable(value = "username") String username) {

		User updateUser = uService.updateUser(newUser, username);
		return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) {
		ApiResponse apiResponse = uService.deleteUser(username);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
