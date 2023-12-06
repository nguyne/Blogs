package com.nguyen.blogs.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyen.blogs.Entity.User;

import jakarta.validation.constraints.NotBlank;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByuserName(@NotBlank String username);
	
	Optional<User> findByUserName(String username);
	
	Boolean existsByuserName (@NotBlank String username);
	
	Boolean existsByEmail (@NotBlank String email);
	
}
