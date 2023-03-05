package com.avakhilkumar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avakhilkumar.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
