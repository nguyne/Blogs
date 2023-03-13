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

import com.nguyen.blogs.Entity.Comment;
import com.nguyen.blogs.Response.ApiResponse;
import com.nguyen.blogs.Service.CommentService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:6666/") // để tránh lỗi CROSS
@RestController
@RequestMapping("/api/v1/post/{post_id}/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("")
	public ResponseEntity<?> getAllComments(@PathVariable(name = "post_id") Integer postId) {

		List<Comment> allComments = commentService.getAllCommentOfPost(postId);

		return new ResponseEntity<>(allComments, HttpStatus.OK);
	}

	@PostMapping("/{username}/{reply_id}")
	public ResponseEntity<?> createComments(@Valid @RequestBody Comment comment,
			@PathVariable(name = "post_id") Integer postId, @PathVariable(name = "username") String username,
			@PathVariable(name = "reply_id") Integer reply_id) {

		Comment newComment = commentService.createComment(comment, postId, username, reply_id);
		return new ResponseEntity<>(newComment, HttpStatus.CREATED);
	}

	@PutMapping("/{username}/{comment_id}")
	public ResponseEntity<?> updateComments(@Valid @RequestBody Comment comment,
			@PathVariable(name = "post_id") Integer postId, @PathVariable(name = "username") String username,
			@PathVariable(name = "comment_id") Integer comment_id) {

		Comment updateComment = commentService.updateComment(comment, comment_id, postId, username);
		return new ResponseEntity<>(updateComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{username}/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable(name = "username") String username,
			@PathVariable(name = "comment_id") Integer comment_id) {
		ApiResponse apiResponse = commentService.deleteComment(comment_id, username);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
