package com.kabita.rms.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
	@Id
	private int roleId;

	private String name;

}
