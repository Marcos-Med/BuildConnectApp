package com.usp.buildconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InteractionDTO {
	private Long client_id;
	private Long post_id;
	private double score;
	private String comment;
}
