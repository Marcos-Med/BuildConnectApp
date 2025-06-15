package com.usp.buildconnect.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateDTO {
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@NotNull
	private Long professional_id;
	private List<String> url_images;
}
