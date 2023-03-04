package com.kabita.rms.entities;

import javax.persistence.CascadeType;
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
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private String address;
	private long phoneNo;
	
	@Column(name = "quantity",columnDefinition = "int default 1")
	private int quantity=1;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserModel user;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Items items;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Billing billing;

}
