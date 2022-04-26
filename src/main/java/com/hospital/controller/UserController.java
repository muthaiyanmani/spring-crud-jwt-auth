package com.hospital.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.User;
import com.hospital.model.UserRequest;
import com.hospital.service.IUserService;
import com.hospital.util.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil util;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user){
		HashMap<String, String> resp= new HashMap<String,String>();
		if(userService.findByEmail(user.getEmail()).isEmpty()) {
			userService.signUp(user);
			resp.put("message", "User created successfully!");
		}else {
			resp.put("message", "Email already exists!");
		}
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody UserRequest user){
		HashMap<String, String> resp= new HashMap<String,String>();
		Optional<User> userData = userService.findByEmail(user.getEmail());
		if(userData.isPresent()) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			String token = util.generateToken(user.getEmail());
			resp.put("message", "Success");
			resp.put("name", userData.get().getName());
			resp.put("token", token);
		}else {
			resp.put("message", "Email or password is incorrect");
		}
		return ResponseEntity.ok(resp);
	}
}
