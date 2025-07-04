package com.planner.Planner_backend.jwtSecurity.controller;

import com.planner.Planner_backend.jwtSecurity.model.Users;
import com.planner.Planner_backend.jwtSecurity.service.JwtService;
import com.planner.Planner_backend.jwtSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {


	@Autowired
	private UserService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;


	@Value("${secret.code}")
	private String expectedSecretCode;

	@RequestMapping("/hello")
	public String greet(){
		return "hello";
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Users user) {
		if (!expectedSecretCode.equals(user.getCode())) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("Code is incorrect");
		}
		Users savedUser = service.saveUser(user);
		return ResponseEntity.ok(savedUser);

	}

	@PostMapping("login")
	public String login(@RequestBody Users user){

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if(authentication.isAuthenticated())
			return jwtService.generateToken(user.getUsername());
		else
			return "Login Failed";

	}

}