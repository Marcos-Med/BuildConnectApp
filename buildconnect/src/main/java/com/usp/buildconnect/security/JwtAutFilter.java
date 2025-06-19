package com.usp.buildconnect.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAutFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(token, userDetails)) {
                	List<SimpleGrantedAuthority> authorities = userDetails.getAuthorities().stream()
                	        .map(auth -> {
                	            String role = auth.getAuthority();
                	            return role.startsWith("ROLE_")
                	                ? new SimpleGrantedAuthority(role)
                	                : new SimpleGrantedAuthority("ROLE_" + role);
                	        })
                	        .toList();
                	UsernamePasswordAuthenticationToken authToken =
                		    new UsernamePasswordAuthenticationToken(userDetails, null,
                		        authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        //System.out.println("DEBUG - Autenticacao no JwtAutFilter ANTES do filterChain:");
        //System.out.println("DEBUG - Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //System.out.println("DEBUG - Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        
        // Chamar o próximo filtro SEMPRE
        filterChain.doFilter(request, response);
        
     // Adicione este bloco também
        //System.out.println("DEBUG - Autenticacao no JwtAutFilter APÓS o filterChain (se o fluxo retornar):");
        //if (SecurityContextHolder.getContext().getAuthentication() != null) {
        //    System.out.println("DEBUG - Principal (APÓS): " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //    System.out.println("DEBUG - Authorities (APÓS): " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        //} else {
        //    System.out.println("DEBUG - Authentication is NULL after filterChain!");
       // }
    }
}
