package com.usp.buildconnect.config;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.usp.buildconnect.security.CustomAuthenticationProvider;
import com.usp.buildconnect.security.JwtAutFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider; //estratégia para busca de Usuário no SGBD
	
	@Value("${jwt.secret}")
	private String secretString;
	
	@Bean
	public SecretKey jwtSecretKey() {
	    // Log para VERIFICAR se secretString está correta AQUI
	    System.out.println("DEBUG - Secret String from properties: " + secretString);
	    byte[] secretBytes = java.util.Base64.getUrlDecoder().decode(secretString);
	    return Keys.hmacShaKeyFor(secretBytes);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtAutFilter jwtAutFilter) throws Exception{ //exceto a rota 'auth/login', qualquer outra precisa de login
		return http
		        .csrf(csrf -> csrf.disable())
		        .authorizeHttpRequests(auth -> auth
		            // Rota de login aberta
		            .requestMatchers("/Auth/Login").permitAll()
		            // CLIENTE
		            //.requestMatchers("/Avaliations/**").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.POST, "/Avaliations").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.PUT, "/Avaliations/ByID").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.DELETE, "/Avaliations/ByID").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.GET, "/Contracts/ByClient").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.POST, "/Contracts").hasRole("CLIENT")
		            .requestMatchers("/Interactions/**").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.GET, "/Notifications/ByClient").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.GET, "/Notifications/ByID").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.DELETE, "/Notifications/ByID").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.GET, "/Posts/ByProfessional").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.GET, "/Posts/ByID").hasRole("CLIENT")
		            .requestMatchers("/Professionals/**").hasRole("CLIENT")
		            .requestMatchers("/Search/**").hasRole("CLIENT")
		            .requestMatchers("/User/**").hasRole("CLIENT")  // CLIENT também acessa User

		            // PROFESSIONAL
		            .requestMatchers(HttpMethod.GET, "/Contracts/ByProfessional").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.PUT, "/Contracts/Accept").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.PUT, "/Contracts/Cancel").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.PUT, "/Contracts/Finish").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.GET, "/Interactions/**").hasRole("PROFESSIONAL")  // Apenas GETs
		            .requestMatchers(HttpMethod.GET, "/Notifications/ByProfessional").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.GET, "/Notifications/ByID").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.DELETE, "/Notifications/ByID").hasRole("PROFESSIONAL")
		            .requestMatchers("/Posts/**").hasRole("PROFESSIONAL")
		            .requestMatchers("/Professionals/**").hasRole("PROFESSIONAL")
		            .requestMatchers("/Search/**").hasRole("PROFESSIONAL")
		            .requestMatchers("/User/**").hasRole("PROFESSIONAL") // PROFESSIONAL também acessa User

		            // Qualquer outro endpoint precisa de autenticação
		            .anyRequest().authenticated()
		        )
		        .addFilterBefore(jwtAutFilter, UsernamePasswordAuthenticationFilter.class)
		        .build();
	}
	
	 @Bean
	 public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		 AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		 authBuilder.authenticationProvider(customAuthenticationProvider);
	     return  authBuilder.build();
	  }

	 @Bean
	 public JwtAuthenticationConverter jwtAuthenticationConverter() {
	        return new JwtAuthenticationConverter() {{
	            setJwtGrantedAuthoritiesConverter(jwt -> {
	                List<GrantedAuthority> authorities = new ArrayList<>();
	                List<String> roles = jwt.getClaimAsStringList("roles");
	                for (String role: roles) {
	                    authorities.add(new SimpleGrantedAuthority(role));
	                }

	                return authorities;
	            });
	        }};
	    }
	 
	 @Bean
	 public JwtDecoder jwtDecoder(SecretKey key) {
	     return NimbusJwtDecoder.withSecretKey(key).build();
	 }
}
