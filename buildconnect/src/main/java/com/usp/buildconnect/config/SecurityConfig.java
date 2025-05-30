package com.usp.buildconnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.usp.buildconnect.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailsService userDetailsService; //estratégia para busca de Usuário no SGBD
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //exceto a rota 'auth/login', qualquer outra precisa de login
		return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login")
                		.permitAll().anyRequest().authenticated()) //define as rotas que precisam de login
                .build();
	}
	
	 @Bean
	 public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		 AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		 authBuilder.userDetailsService((UserDetailsService)userDetailsService) //Realiza a autenticação do Usuário
	                .passwordEncoder(passwordEncoder());
	     return  authBuilder.build();
	  }

	 @Bean
	 public PasswordEncoder passwordEncoder() { //Realiza Criptografia na Senha
	        return new BCryptPasswordEncoder();
	 }
}
