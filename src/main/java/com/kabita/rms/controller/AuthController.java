package com.kabita.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabita.rms.entities.UserModel;
import com.kabita.rms.payload.JwtAuthRequest;
import com.kabita.rms.payload.JwtAuthResponse;
import com.kabita.rms.repository.UserRepository;
import com.kabita.rms.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/auth/login")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepo;


	public void authenticate(String email, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				email, password);

		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}

	@PostMapping("/")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
		this.authenticate(request.getEmail(), request.getPassword());

//		getting the user based on the email provided
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());

		String token = this.jwtTokenHelper.generateToken(userDetails);
		UserModel userModel = userRepo.findByEmail(request.getEmail()).orElseThrow();

//		setting the jwt response
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		jwtAuthResponse.setEmail(request.getEmail());
		jwtAuthResponse.setName(userModel.getFullName());

		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
	}
}
