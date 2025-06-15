package com.usp.buildconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.services.UserService;

@RestController
@RequestMapping("/Users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@DeleteMapping("/ByID")
	public ResponseEntity<Void> deleteUser(@RequestParam("user_id") Long id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
