package com.kabita.rms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabita.rms.payload.ApiResponse;
import com.kabita.rms.payload.UserDto;
import com.kabita.rms.servicesImpl.UserServicesImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserServicesImpl userService;

	@PostMapping("/")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto user = this.userService.createUser(userDto);
		if (user == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email already taken", false),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> user = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(user, HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);

		if (updatedUser == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email already taken", false),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		UserDto userDto = this.userService.getSingleUser(userId);

		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}
}
