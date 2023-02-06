package com.avakhilkumar.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avakhilkumar.blog.entities.Category;
import com.avakhilkumar.blog.entities.Post;
import com.avakhilkumar.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);

}
