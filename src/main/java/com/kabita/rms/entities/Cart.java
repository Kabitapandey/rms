package com.kabita.rms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;
	@OneToOne
	@JoinColumn(name = "userId")
	private UserModel user;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Items items;
	@Column(columnDefinition = "int default '1'")
	private int quantity;
}
