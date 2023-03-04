package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.BookingDto;

public interface BookingServices {
	void deleteBooking(Integer bookingId);

	BookingDto addBooking(BookingDto bookingDto,Integer userId,Integer tableId);

	List<BookingDto> getAllBookings();

	List<BookingDto> getBookingByUser(Integer userId);

	BookingDto updateBooking(BookingDto bookingDto, Integer bookingId);

	BookingDto getSingleBooking(Integer bookingId);
}
