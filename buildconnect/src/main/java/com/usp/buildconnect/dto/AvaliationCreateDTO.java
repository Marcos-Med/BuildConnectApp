package com.usp.buildconnect.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvaliationCreateDTO {
	@NotNull
	private Long client_id;
	@NotNull
	private Long professional_id;
	@NotNull @PositiveOrZero
	private double score;
	private String comment;
}
