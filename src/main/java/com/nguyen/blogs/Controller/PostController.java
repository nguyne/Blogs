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

import com.nguyen.blogs.Entity.Post;
import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Response.ApiResponse;
import com.nguyen.blogs.Service.PostService;
import com.nguyen.blogs.Service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:6666/") // để tránh lỗi CROSS
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;

	@GetMapping("")
	public ResponseEntity<?> getListAllPost() {
		try {
			List<Post> posts = postService.getAllPosts();
			return ResponseEntity.ok(posts);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<?> getListAllPostOfUser(@PathVariable(value = "username") String username) {
		try {

			User user = userService.findByuserName(username);
			List<Post> posts = postService.getAllPostsOfUser(user);
			return ResponseEntity.ok(posts);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{username}")
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post,
			@PathVariable(value = "username") String username) {
		Post newPost = postService.createPost(post, username);
		return new ResponseEntity<>(newPost, HttpStatus.CREATED);

	}


	@PutMapping("/{username}/{id}")
	public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post,
			@PathVariable(value = "username") String username, @PathVariable(value = "id") Integer id) {
		Post updatePost = postService.updatePost(post, id, username);
		return new ResponseEntity<>(updatePost, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{username}/{id}")
	public ResponseEntity<?> deletePost(@PathVariable(value = "username") String username,
			@PathVariable(value = "id") Integer id) {
		ApiResponse apiResponse = postService.deletePost(username, id);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/search/{title}")
	public ResponseEntity<?> searchPostByTitle(@PathVariable(value = "title") String title) {
		List<Post> posts = postService.searchPostByTitle(title);
		return ResponseEntity.ok(posts);
	}

}
