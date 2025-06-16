package com.usp.buildconnect.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) { //gera token JWT
    	System.out.println(secret);
        return Jwts.builder()
                .setSubject(username) //pertence ao Usuário "username"
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h de expiração
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractUsername(String token) { //Extrai o Usuário Dono do token
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean isTokenExpired(String token) { //verifica se o token expirou
    	Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody().getExpiration();
    	return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) { //verifica se o token não expirou e se bate o username informado
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}