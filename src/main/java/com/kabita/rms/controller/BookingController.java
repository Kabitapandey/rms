package com.kabita.rms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabita.rms.payload.ApiResponse;
import com.kabita.rms.payload.BookingDto;
import com.kabita.rms.payload.TablesDto;
import com.kabita.rms.servicesImpl.BookingServiceImpl;
import com.kabita.rms.servicesImpl.TablesServicesImpl;

@RestController
@RequestMapping("/api")
public class BookingController {
	@Autowired
	BookingServiceImpl bookingService;

	@Autowired
	TablesServicesImpl tableService;

	@PostMapping("/booking/user/{userId}/table/{tableId}")
	public ResponseEntity<?> addBooking(@Valid @RequestBody BookingDto bookingDto, @PathVariable Integer userId,
			@PathVariable Integer tableId) {
		TablesDto table = this.tableService.getSingleTable(tableId);
//checking table is available or not
		if (table.isBooked()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Table has already been booked", false),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<BookingDto>(this.bookingService.addBooking(bookingDto, userId, tableId),
				HttpStatus.OK);
	}

	@GetMapping("/booking")
	public ResponseEntity<List<BookingDto>> getAllBookings() {
		return new ResponseEntity<List<BookingDto>>(this.bookingService.getAllBookings(), HttpStatus.OK);
	}

	@GetMapping("user/{userId}/booking")
	public ResponseEntity<List<BookingDto>> getBookingByUser(@PathVariable Integer userId) {
		return new ResponseEntity<List<BookingDto>>(this.bookingService.getBookingByUser(userId), HttpStatus.OK);
	}

	@PutMapping("booking/{bookingId}")
	public ResponseEntity<BookingDto> updateBooking(@RequestBody BookingDto bookingDto,
			@PathVariable Integer bookingId) {
		return new ResponseEntity<BookingDto>(this.bookingService.updateBooking(bookingDto, bookingId), HttpStatus.OK);
	}

	@DeleteMapping("booking/{bookingId}")
	public ResponseEntity<ApiResponse> deleteBooking(@PathVariable Integer bookingId) {
		bookingService.deleteBooking(bookingId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Booking cancelled successfully", true), HttpStatus.OK);
	}
}
