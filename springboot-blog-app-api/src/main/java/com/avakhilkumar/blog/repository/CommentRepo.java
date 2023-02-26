package com.avakhilkumar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avakhilkumar.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
}
