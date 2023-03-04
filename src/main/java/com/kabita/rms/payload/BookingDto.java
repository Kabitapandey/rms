package com.kabita.rms.payload;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto {
	private int bookingId;
	private Date date;
	private UserDto user;
	private TablesDto table;
}
