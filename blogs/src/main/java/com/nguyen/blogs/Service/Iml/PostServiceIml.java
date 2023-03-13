package com.nguyen.blogs.Service.Iml;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nguyen.blogs.Entity.Post;
import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Repository.PostRepository;
import com.nguyen.blogs.Repository.UserRepository;
import com.nguyen.blogs.Response.ApiResponse;
import com.nguyen.blogs.Service.PostService;
import com.nguyen.blogs.exception.AccessDeniedException;
import com.nguyen.blogs.exception.UnauthorizedException;

@Component
public class PostServiceIml implements PostService{
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;


	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}
	
	@Override
	public List<Post> getAllPostsOfUser(User user) {
		return postRepository.findAllPostByUser(user);
	}

	@Override
	public Post createPost(Post post,String username) {
		User user = userRepository.findByuserName(username);
		if(user != null) {
			post.setDateTime(LocalDateTime.now());
			post.setUser(user);
			return postRepository.save(post);
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "User: " + username +" not found!!");
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public Post updatePost(Post post, Integer id, String username) {
		if(postRepository.findById(id).isEmpty()) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment!!");
			throw new AccessDeniedException(apiResponse);
		}
		User user = userRepository.findByuserName(username);
		Post newPost = postRepository.findById(id).get();
		if(newPost != null) {
			if(newPost.getUser().getId().equals(user.getId())) {
				newPost.setTitle(post.getTitle());
				newPost.setContent(post.getContent());
				newPost.setStatus(post.getStatus());
				newPost.setDateTimeUpdate(LocalDateTime.now());
				
				return postRepository.save(newPost);
			}
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update Post: " + id);
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public ApiResponse deletePost(String username, Integer id) {
		if(postRepository.findById(id).isEmpty()) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment!!");
			throw new AccessDeniedException(apiResponse);
		}
		Post post = postRepository.findById(id).get();
		User user = userRepository.findByuserName(username);
		if(user == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't delete Post: " + id);
			throw new AccessDeniedException(apiResponse);
		}
		if(post.getUser().getId() != user.getId()) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't delete Post: " + id);
			throw new AccessDeniedException(apiResponse);
		}
		postRepository.deleteById(post.getId());
		return new ApiResponse(Boolean.TRUE, "You successfully deleted Post: " + id);
	}

	@Override
	public List<Post> searchPostByTitle(String title) {
		return postRepository.findByTitle(title);
	}

}
