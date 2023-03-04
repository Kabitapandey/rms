package com.kabita.rms.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtAuthRequest {
	private String email;
	private String password;
}
