package com.avakhilkumar.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4, message="Minimum of 4 characters is required for the Title.")
	
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10, message = "Minimum of 10 characters is required for the Category Description.")
	private String categoryDescription;
}

