package com.nguyen.blogs.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyen.blogs.Entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findAllByPostId(Integer id);
}
