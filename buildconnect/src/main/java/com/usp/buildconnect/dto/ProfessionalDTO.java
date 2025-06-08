package com.usp.buildconnect.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfessionalDTO {
	private Long id;
	private String name;
	private String cpf;
	private List<String> services;
	private String MEI;
	private double mean_avaliation;
	private String region;
}
