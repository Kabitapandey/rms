package com.kabita.rms.entities;

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
public class Billing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billId;
	@ManyToOne
	private UserModel user;
	private String paidStatus;
	@OneToOne
	@JoinColumn(name="booking_id")
	private Booking booking;
	@OneToOne
	@JoinColumn(name="order_id")
	private Orders order;
	private float price;
}
