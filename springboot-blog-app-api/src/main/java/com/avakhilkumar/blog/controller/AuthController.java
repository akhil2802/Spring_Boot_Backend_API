package com.avakhilkumar.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avakhilkumar.blog.exceptions.ApiException;
import com.avakhilkumar.blog.payloads.JwtAuthRequest;
import com.avakhilkumar.blog.payloads.JwtAuthResponse;
import com.avakhilkumar.blog.payloads.UserDto;
import com.avakhilkumar.blog.security.JwtTokenHelper;
import com.avakhilkumar.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authManager;   // to authenticate password
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetail = this.userDetailService.loadUserByUsername(request.getUsername());
		
		String generatedToken = this.jwtTokenHelper.generateToken(userDetail);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(generatedToken);
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		try {
			this.authManager.authenticate(authToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details.");
			throw new ApiException("Invalid username or password.");
		}
	}
	
//	Register New User API:
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
}
