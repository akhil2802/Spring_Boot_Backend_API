package com.avakhilkumar.blog.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	@NotBlank
	private String title;
	@NotBlank
	@Size(min = 10, max = 10000, message = "Content must be minimum of 3 characters and maximum of 10 characters!")
	private String content;
	private String imageName; 
	private Date addedDate;
	private UserDto user;
	private CategoryDto category;
}
