package com.usp.buildconnect.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.usp.buildconnect.entity.User;
import com.usp.buildconnect.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
    private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new BadCredentialsException("User Not Found"));

        String key = user.getPassword();

        if (!key.startsWith("$2a$")) {
            // Senha em texto plano
            if (!rawPassword.equals(key)) {
                throw new BadCredentialsException("Invalid Password");
            }
            // Migra para BCrypt
            userDetailsService.migratePasswordIfNeeded(user, rawPassword);
        } else {
            // Senha já criptografada
            if (!passwordEncoder.matches(rawPassword, key)) {
                throw new BadCredentialsException("Invalid Password");
            }
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
