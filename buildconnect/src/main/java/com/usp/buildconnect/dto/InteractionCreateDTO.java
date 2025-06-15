package com.usp.buildconnect.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class InteractionCreateDTO {
	@NotNull
	private Long client_id;
	@NotNull
	private Long post_id;
	@NotNull @PositiveOrZero
	private double score;
	private String comment;
}
