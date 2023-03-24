package com.nguyen.blogs.Service.Iml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Repository.UserRepository;
import com.nguyen.blogs.Response.ApiResponse;
import com.nguyen.blogs.Service.UserService;
import com.nguyen.blogs.exception.AccessDeniedException;
import com.nguyen.blogs.exception.BadRequestException;
import com.nguyen.blogs.exception.UnauthorizedException;

@Component
public class UserServiceIml implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> getListUsers() {
		return userRepository.findAll();
	}
	@Override
	public User createUsers(User user) {
		if(userRepository.existsByuserName(user.getUserName())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Username is already taken");
			throw new BadRequestException(apiResponse);
		}
		if(userRepository.existsByEmail(user.getEmail())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Email is already taken");
			throw new BadRequestException(apiResponse);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	@Override
	public User updateUser(User newUser, String username) {
		User user = userRepository.findByuserName(username);
		if(user != null) {
			user.setLastName(newUser.getLastName());
			user.setFirstName(newUser.getFirstName());
			user.setAddress(newUser.getAddress());
			user.setPassword(passwordEncoder.encode(newUser.getPassword()));
			return userRepository.save(user);
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update: " + username);
		throw new UnauthorizedException(apiResponse);
	}
	@Override
	public ApiResponse deleteUser(String username) {
		User user = userRepository.findByuserName(username);
		if(user == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't delete: " + username);
			throw new AccessDeniedException(apiResponse);
		}
		userRepository.deleteById(user.getId());
		return new ApiResponse(Boolean.TRUE, "You successfully deleted of: " + username);
	}
	@Override
	public User findByuserName(String username) {
		return userRepository.findByuserName(username);
	}

}
