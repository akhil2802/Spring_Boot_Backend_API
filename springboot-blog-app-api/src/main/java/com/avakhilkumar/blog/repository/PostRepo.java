package com.avakhilkumar.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avakhilkumar.blog.entities.Category;
import com.avakhilkumar.blog.entities.Post;
import com.avakhilkumar.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	//	List<Post> findByTitleContaining(String title);
	
	// SEARCH Using JPQL:
	@Query("SELECT post FROM Post post WHERE post.title LIKE :key")
	List<Post> searchByTitle(@Param("key") String title);
}
