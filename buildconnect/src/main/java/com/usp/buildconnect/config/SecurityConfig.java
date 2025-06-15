package com.usp.buildconnect.config;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
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
		        .authorizeHttpRequests(auth -> auth
		            // Rota de login aberta
		            .requestMatchers("/Auth/Login").permitAll()

		            // CLIENTE
		            .requestMatchers("/Avaliations/**").hasRole("CLIENT")
		            .requestMatchers(HttpMethod.GET, "/Contracts/ByID").hasRole("CLIENT")
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
		            .requestMatchers(HttpMethod.GET, "/Avaliations/ByID").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.GET, "/Avaliations/ByProfessional").hasRole("PROFESSIONAL")
		            .requestMatchers(HttpMethod.GET, "/Contracts/ByID").hasRole("PROFESSIONAL")
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
		        .oauth2ResourceServer(oauth2 -> oauth2
		            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
		        )
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
	 
	 @Bean
	    public JwtAuthenticationConverter jwtAuthenticationConverter() {
	        return new JwtAuthenticationConverter() {{
	            setJwtGrantedAuthoritiesConverter(jwt -> {
	                List<GrantedAuthority> authorities = new ArrayList<>();
	                String role = jwt.getClaimAsString("role");

	                if (role != null) {
	                    authorities.add(new SimpleGrantedAuthority(role));
	                }

	                return authorities;
	            });
	        }};
	    }
	 
	 @Bean
	 public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secretKey) {
	     // Exemplo com chave simétrica (usada com jjwt, por exemplo)
	     return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")).build();
	  }
}
