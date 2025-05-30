package com.usp.buildconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest { //Solicitação de Autenticação
	private String username;
	private String password;
}
