package com.kabita.rms.payload;

import javax.validation.constraints.NotEmpty;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
	private int orderId;
	@NotEmpty
	private String address;
	private long phoneNo;
	private int quantity;
	@NonNull
	private UserDto user;
	@NonNull
	private ItemsDto items;
}
