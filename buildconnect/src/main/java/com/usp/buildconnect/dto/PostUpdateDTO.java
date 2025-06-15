package com.usp.buildconnect.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateDTO {
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	private List<String> url_images;
}
