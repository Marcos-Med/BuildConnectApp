package com.usp.buildconnect.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	
	@Autowired
	private SecretKey secretKey;

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) { //gera token JWT
    	List<String> roles = authorities.stream()
    		    .map(auth -> auth.getAuthority().startsWith("ROLE_") ? auth.getAuthority() : "ROLE_" + auth.getAuthority())
    		    .collect(Collectors.toList());
    	Map<String, Object> claims = new HashMap<>();
    	claims.put("authorities", roles);
        return Jwts.builder()
                .header() // Inicia a construção do cabeçalho
                .add("alg", "HS512") // Adiciona o algoritmo HS512 ao cabeçalho JWT
                .and() // Retorna ao JwtBuilder
                .claims().add(claims)
                .and()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10)) // 10h de expiração
                .signWith(secretKey) // Assina apenas com a chave secreta
                .compact();
    }

    public String extractUsername(String token) { //Extrai o Usuário Dono do token
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
    
    public boolean isTokenExpired(String token) { //verifica se o token expirou
    	Date expiration = Jwts.parser() 
                .verifyWith(secretKey) 
                .build() 
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    	return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) { //verifica se o token não expirou e se bate o username informado
    	return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}