package com.usp.buildconnect.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.usp.buildconnect.entity.User;
import com.usp.buildconnect.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
    
    @Transactional
    public void migratePasswordIfNeeded(User user, String rawPassword) {
        if (!user.getPassword().startsWith("$2a$")) {
            // Senha ainda está em texto plano, vamos criptografar e salvar
            String encoded = passwordEncoder.encode(rawPassword);
            user.setPassword(encoded);
            userRepository.save(user);
        }
    }
}