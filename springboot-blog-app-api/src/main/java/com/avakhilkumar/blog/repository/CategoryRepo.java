package com.avakhilkumar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avakhilkumar.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
