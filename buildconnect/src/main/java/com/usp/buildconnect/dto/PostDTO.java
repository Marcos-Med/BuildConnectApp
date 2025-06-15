package com.usp.buildconnect.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDTO {
	private Long id;
	private String title;
	private String description;
	private List<String> urls_image;	
}
