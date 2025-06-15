package com.usp.buildconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDTO {
	private Long id;
	private String message;
	private Long contract_id;
}
