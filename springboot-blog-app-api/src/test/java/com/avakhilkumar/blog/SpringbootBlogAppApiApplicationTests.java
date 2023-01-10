package com.avakhilkumar.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.avakhilkumar.blog.repository.UserRepo;

@SpringBootTest
class SpringbootBlogAppApiApplicationTests {
	
	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void userRepoTest() {
		String userRepoClassName = this.userRepo.getClass().getName();
		Package userRepoPackageName = this.userRepo.getClass().getPackage();
		System.out.println("userRepo belongs to the class : " + userRepoClassName);
		System.out.println("userRepo belongs to the Package : " + userRepoPackageName);
	}

}
