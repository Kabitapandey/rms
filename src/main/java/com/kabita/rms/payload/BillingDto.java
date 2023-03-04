package com.kabita.rms.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillingDto {
	private int billId;
	private float price;
	private String paidStatus;
	private int quantity;
	private UserDto user;
	private BookingDto booking;
	private OrderDto order;
}
