package com.kabita.rms.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
	private String email;

	public UserNotFoundException(String email) {
		super(email + "does not exist");
		this.email = email;
	}
}
