package com.usp.buildconnect.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvaliationUpdateDTO {
	@NotNull @PositiveOrZero
	private double score;
	private String comment;
}
