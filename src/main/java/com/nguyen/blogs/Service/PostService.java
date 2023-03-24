package com.nguyen.blogs.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nguyen.blogs.Entity.Post;
import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Response.ApiResponse;

@Service
public interface PostService {

	List<Post> getAllPosts();
	
	List<Post> getAllPostsOfUser(User user);
	
	Post createPost(Post post,String username);
	
	Post updatePost(Post post, Integer id, String username);
	
	ApiResponse deletePost(String username, Integer id);
	
	List<Post> searchPostByTitle(String title);
}
