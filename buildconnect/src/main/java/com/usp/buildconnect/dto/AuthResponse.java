package com.usp.buildconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse { //Resposta da Autenticação, contenco Token JWT
	private String token;
}
