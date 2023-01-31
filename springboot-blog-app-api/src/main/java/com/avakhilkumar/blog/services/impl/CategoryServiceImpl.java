package com.avakhilkumar.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avakhilkumar.blog.entities.Category;
import com.avakhilkumar.blog.exceptions.ResourceNotFoundException;
import com.avakhilkumar.blog.payloads.CategoryDto;
import com.avakhilkumar.blog.repository.CategoryRepo;
import com.avakhilkumar.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	// CREATE:
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}
	
	// UPDATE:
	
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	// DELETE: 
	
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		this.categoryRepo.delete(category);
	}

	// GET_ONE:
	
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	// GET_ALL:
	
	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
