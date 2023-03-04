package com.kabita.rms.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
	private String token;
	private String name;
	private String email;
}
