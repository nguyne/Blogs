package com.nguyen.blogs.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Response.ApiResponse;

@Service
public interface UserService {

	List<User> getListUsers();
	
	User findByuserName(String username);
	
	User createUsers(User user);
	
	User updateUser(User newUser, String username);
	
	ApiResponse deleteUser(String username);
}
