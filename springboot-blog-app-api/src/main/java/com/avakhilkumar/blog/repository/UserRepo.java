package com.avakhilkumar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avakhilkumar.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
