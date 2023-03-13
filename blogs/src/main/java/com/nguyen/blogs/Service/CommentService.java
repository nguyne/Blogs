package com.nguyen.blogs.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nguyen.blogs.Entity.Comment;
import com.nguyen.blogs.Response.ApiResponse;

@Service
public interface CommentService {
	
	Comment createComment(Comment comment, Integer post_id, String username, Integer IdReply);
	
	Comment updateComment(Comment comment, Integer comment_id, Integer post_id, String username);
	
	ApiResponse deleteComment(Integer comment_id, String username);
	
	List<Comment> getAllCommentOfPost(Integer id);
	
}
