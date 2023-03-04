package com.kabita.rms.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	private Date date;
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserModel user;
	@ManyToOne
	@JoinColumn(name="table_id")
	private Tables table;
	
	@OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
	private Billing billing;
}
