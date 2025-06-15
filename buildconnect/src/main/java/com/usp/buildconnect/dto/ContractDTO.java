package com.usp.buildconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContractDTO {
	private Long id;
	private String description;
	private double value;
	private String date_init;
	private String date_end;
	private String status;
	private String address;
	private Long service_id;
	private Long professional_id;
	private Long client_id;
}
