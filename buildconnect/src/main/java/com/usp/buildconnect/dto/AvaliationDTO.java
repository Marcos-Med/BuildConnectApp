package com.usp.buildconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvaliationDTO {
	private Long professional_id;
	private Long client_id;
	private double score;
	private String comment;
}
