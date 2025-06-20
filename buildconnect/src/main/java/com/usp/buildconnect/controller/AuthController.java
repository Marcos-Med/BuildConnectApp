package com.usp.buildconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.dto.AuthRequest;
import com.usp.buildconnect.dto.AuthResponse;
import com.usp.buildconnect.security.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authManager; //injeta automaticamente o autenticador do ApplicationContext
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/Login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            ); //verifica se user e password estão corretos
            
            String token = jwtUtil.generateToken(auth.getName(), auth.getAuthorities());
            return ResponseEntity.ok(new AuthResponse(token)); //emite o token de autorização

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //falha de login
        }
    }
	
}
