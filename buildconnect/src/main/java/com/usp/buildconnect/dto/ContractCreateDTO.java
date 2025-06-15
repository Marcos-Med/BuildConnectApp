package com.usp.buildconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContractCreateDTO {
	@NotBlank
	private String description;
	@NotNull @PositiveOrZero
	private double value;
	@NotBlank
	private String date_init;
	@NotBlank
	private String date_end;
	@NotBlank
	private String status;
	@NotBlank
	private String address;
	@NotNull
	private Long service_id;
	@NotNull
	private Long professional_id;
	@NotNull
	private Long client_id;
}
