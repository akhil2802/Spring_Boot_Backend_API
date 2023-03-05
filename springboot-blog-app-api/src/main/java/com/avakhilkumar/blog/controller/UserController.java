package com.avakhilkumar.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avakhilkumar.blog.payloads.ApiResponse;
import com.avakhilkumar.blog.payloads.UserDto;
import com.avakhilkumar.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	// POST - Create User:
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
	}
	
	// PUT - Update User:
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uId) {
		UserDto updatedUser = this.userService.updateUser(userDto, uId);
		return ResponseEntity.ok(updatedUser);
	}
	
	// DELETE - Delete User:
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId) {
		this.userService.deleteUser(uId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully.", true), HttpStatus.OK);
	}
	/* We can also return like this:
		@DeleteMapping("/{userId}")
		public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId) {
			this.userService.deleteUser(uId);
			return ResponseEntity.ok(Map.of("message", "User deleted successfully."));
			return new ResponseEntity(Map.of("message", "User deleted successfully."), HttpStatus.OK);
		}
	*/
	
	// GET - Get All Users:  
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	// GET - Get Single User: 
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
