package com.usp.buildconnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usp.buildconnect.entity.User;
import com.usp.buildconnect.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
		userRepository.delete(user);
	}
}
