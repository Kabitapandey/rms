package com.kabita.rms.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kabita.rms.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
//catching resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
	}

//handling of methodargumentnotvalidexception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {
		Map<String, String> msg = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((err) -> {
			String fieldName = ((FieldError) err).getField();
			String fieldMsg = err.getDefaultMessage();

			msg.put(fieldName, fieldMsg);
		});

		return new ResponseEntity<Map<String, String>>(msg, HttpStatus.BAD_REQUEST);
	}

//handling of user not found exception
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse> userNotFoundExceptionHandler(UserNotFoundException ex) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
	}

}
