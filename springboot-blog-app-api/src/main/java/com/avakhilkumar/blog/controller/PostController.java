package com.avakhilkumar.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avakhilkumar.blog.config.AppConstants;
import com.avakhilkumar.blog.payloads.ApiResponse;
import com.avakhilkumar.blog.payloads.PostDto;
import com.avakhilkumar.blog.payloads.PostResponse;
import com.avakhilkumar.blog.services.FileService;
import com.avakhilkumar.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService; 
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// CREATE:
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId); 
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	} 
	
	// UPDATE POST:
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	// GET ALL POSTS:
	
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPosts(
				@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
				@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, 
				@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
				@RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
			
			PostResponse postDtos = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
			return new ResponseEntity<PostResponse>(postDtos, HttpStatus.OK); 
		}
		
	// GET ONE POST:
		
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId) {
			
			return ResponseEntity.ok(this.postService.getPostById(postId));
		}
		
	// GET POST BY USER:
		
		@GetMapping("/user/{userId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
			
			List<PostDto> postDtos = this.postService.getPostsByUser(userId);
			
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
	// GET POST BY CATEGORY:
		
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
			
			List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
			
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
	// DELETE POST:
		
		@DeleteMapping("/posts/{postId}")
		public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
			
			this.postService.deletePost(postId); 
			
			return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully.", true), HttpStatus.OK);
		}
		
	// SEARCH POST:
		
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords) {
			
			List<PostDto> searchedPosts = this.postService.searchPosts(keywords);
			
			return new ResponseEntity<List<PostDto>>(searchedPosts, HttpStatus.OK);
		}
							
	// IMAGE UPLOAD AND SERVE-UP ON BROWSER:
		@PostMapping("post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
			
			PostDto postDto = this.postService.getPostById(postId);
			
			String uploadedImageName = this.fileService.uploadImage(path, image);
			
			
			postDto.setImageName(uploadedImageName);
			PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
		}
		
	// SERVE A FILE:
		@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
			
			InputStream resource = this.fileService.getResource(path, imageName);
			
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			
			StreamUtils.copy(resource, response.getOutputStream());
		}
		
		
}
