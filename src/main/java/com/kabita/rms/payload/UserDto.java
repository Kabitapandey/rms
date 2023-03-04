package com.kabita.rms.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.kabita.rms.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private int userId;
	@NotEmpty
	private String fullName;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min = 7, max = 50)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private Set<Role> roles = new HashSet<>();
}
