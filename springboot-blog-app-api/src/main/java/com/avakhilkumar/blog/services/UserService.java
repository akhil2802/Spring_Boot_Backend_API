package com.avakhilkumar.blog.services;


import java.util.List;

import com.avakhilkumar.blog.payloads.UserDto;

public interface UserService {
	
//	to create special user with access role and password:
	UserDto registerNewUser(UserDto user);
	
//	to create normal user:
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
