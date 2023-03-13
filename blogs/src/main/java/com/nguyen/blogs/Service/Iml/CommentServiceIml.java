package com.nguyen.blogs.Service.Iml;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nguyen.blogs.Entity.Comment;
import com.nguyen.blogs.Entity.Post;
import com.nguyen.blogs.Entity.User;
import com.nguyen.blogs.Repository.CommentRepository;
import com.nguyen.blogs.Repository.PostRepository;
import com.nguyen.blogs.Repository.UserRepository;
import com.nguyen.blogs.Response.ApiResponse;
import com.nguyen.blogs.Service.CommentService;
import com.nguyen.blogs.exception.AccessDeniedException;
import com.nguyen.blogs.exception.BadRequestException;
import com.nguyen.blogs.exception.UnauthorizedException;

@Component
public class CommentServiceIml implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment createComment(Comment comment, Integer post_id, String username, Integer IdReply) {
		if(postRepository.findById(post_id).isEmpty()) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment!!");
			throw new AccessDeniedException(apiResponse);
		}
		Post post = postRepository.findById(post_id).get();
		User user = userRepository.findByuserName(username);
		if (post != null && user != null) {
			comment.setUser(user);
			comment.setPost(post);
			comment.setDateTime(LocalDateTime.now());
			comment.setIdReply(IdReply);
			return commentRepository.save(comment);
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "User or post not found!!");
		throw new UnauthorizedException(apiResponse);

	}

	@Override
	public Comment updateComment(Comment comment, Integer comment_id, Integer post_id, String username) {
		if(postRepository.findById(post_id).isEmpty() || commentRepository.findById(comment_id).isEmpty()) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment!!");
			throw new AccessDeniedException(apiResponse);
		}
		Post post = postRepository.findById(post_id).get();
		User user = userRepository.findByuserName(username);
		Comment updateComment = commentRepository.findById(comment_id).get();
		if (user == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment!!");
			throw new AccessDeniedException(apiResponse);
		}
		if (!post.getId().equals(updateComment.getPost().getId())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment");
			throw new AccessDeniedException(apiResponse);
		}
		if (updateComment.getUser().getId().equals(user.getId())) {
			updateComment.setCommentContent(comment.getCommentContent());
			updateComment.setDateTime(LocalDateTime.now());
			return commentRepository.save(updateComment);
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update comment!!");
		throw new BadRequestException(apiResponse);
	}

	@Override
	public ApiResponse deleteComment(Integer comment_id, String username) {
		if(commentRepository.findById(comment_id).isEmpty()) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't delete comment: " + comment_id);
			throw new AccessDeniedException(apiResponse);
		}
		Comment comment = commentRepository.findById(comment_id).get();
		User user = userRepository.findByuserName(username);
		if(user == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't delete comment: " + comment_id);
			throw new AccessDeniedException(apiResponse);
		}
		if(!comment.getUser().getId().equals(user.getId())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't delete comment: " + comment_id);
			throw new AccessDeniedException(apiResponse);
		}
		commentRepository.deleteById(comment_id);
		return new ApiResponse(Boolean.TRUE, "You successfully deleted comment: " + comment_id);
	}

	@Override
	public List<Comment> getAllCommentOfPost(Integer id) {
		return commentRepository.findAllByPostId(id);
	}

}
