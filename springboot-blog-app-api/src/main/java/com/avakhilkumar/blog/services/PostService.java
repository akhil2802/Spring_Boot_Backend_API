package com.avakhilkumar.blog.services;

import java.util.List;

import com.avakhilkumar.blog.entities.Post;
import com.avakhilkumar.blog.payloads.PostDto;
import com.avakhilkumar.blog.payloads.PostResponse;

public interface PostService {
	
	// Create:
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// Update:
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// Delete:
	
	void deletePost(Integer postId);
	
	// Get All Posts:
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	
	// Get One Post:
	
	PostDto getPostById(Integer postId);
	
	// Get All Post by Category:
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// Get All Post by User:
	
	List<PostDto> getPostsByUser(Integer userId);
	
	// Search Posts:
	
	List<PostDto> searchPosts(String keyword);
}
