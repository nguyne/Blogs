package com.nguyen.blogs.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nguyen.blogs.Entity.Post;
import com.nguyen.blogs.Entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findAll();
	
	List<Post> findAllPostByUser(User user);

	@Query(value = "FROM Post post WHERE post.status = 1 AND post.title ILIKE %:title%")
	List<Post> findByTitle(@Param("title") String title);
}
