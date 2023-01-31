package com.avakhilkumar.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avakhilkumar.blog.payloads.ApiResponse;
import com.avakhilkumar.blog.payloads.CategoryDto;
import com.avakhilkumar.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	// CREATE:
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	
	// UPDATE:
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	// DELETE:
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category with Id: " + categoryId + " is deleted Successfully!", true), HttpStatus.OK);
	}
	
	// GET_ONE:
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
		
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}
	
	// GET_ALL:
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categoryDtos = this.categoryService.getCategories();
		
		return ResponseEntity.ok(categoryDtos);
	}
	
}
