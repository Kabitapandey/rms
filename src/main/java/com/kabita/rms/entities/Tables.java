package com.kabita.rms.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tables {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tableId;
	private boolean booked;
	private float price;
	
	@OneToMany(mappedBy = "table",cascade = CascadeType.ALL)
	List<Booking> booking;
}
