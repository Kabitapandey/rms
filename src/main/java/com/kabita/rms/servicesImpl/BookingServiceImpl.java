package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Booking;
import com.kabita.rms.entities.Tables;
import com.kabita.rms.entities.UserModel;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.BillingDto;
import com.kabita.rms.payload.BookingDto;
import com.kabita.rms.repository.BookingRepository;
import com.kabita.rms.repository.TableRepository;
import com.kabita.rms.repository.UserRepository;
import com.kabita.rms.services.BookingServices;

@Service
public class BookingServiceImpl implements BookingServices {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BookingRepository bookingRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	TableRepository tableRepo;

	@Autowired
	BillingServiceImpl billingService;

	@Override
	public void deleteBooking(Integer bookingId) {
		Booking booking = this.bookingRepo.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "Booking Id", bookingId));

//		getting table object and setting the booked to false if booking is canceled
		booking.getTable().setBooked(false);
		booking.getTable().setPrice(booking.getTable().getPrice());

		this.tableRepo.save(booking.getTable());

		this.bookingRepo.delete(booking);

	}

	@Override
	public BookingDto addBooking(BookingDto bookingDto, Integer userId, Integer tableId) {
//		getting booking data from user
		Booking booking = this.modelMapper.map(bookingDto, Booking.class);
//		getting user object from database
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
//		getting table object from database
		Tables table = this.tableRepo.findById(tableId)
				.orElseThrow(() -> new ResourceNotFoundException("Table", "Table Id", tableId));

		booking.setDate(bookingDto.getDate());
		booking.setUser(user);
		booking.setTable(table);

		table.setBooked(true);
		this.tableRepo.save(table);

		Booking addedBooking = this.bookingRepo.save(booking);

		BillingDto billingDto = new BillingDto();
		billingDto.setPaidStatus("unpaid");
		billingDto.setPrice(table.getPrice());
		int bookingId = booking.getBookingId();

		billingService.addBookingBilling(billingDto, userId, bookingId);

		return this.modelMapper.map(addedBooking, BookingDto.class);
	}

	@Override
	public List<BookingDto> getAllBookings() {
		List<Booking> bookings = this.bookingRepo.findAll();
//taking data as stream and converting it into bookingdto type
		return bookings.stream().map((booking) -> this.modelMapper.map(booking, BookingDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookingDto> getBookingByUser(Integer userId) {
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Booking> bookings = this.bookingRepo.getBookingsByUser(user);

		return bookings.stream().map((booking) -> this.modelMapper.map(booking, BookingDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public BookingDto updateBooking(BookingDto bookingDto, Integer bookingId) {
//		getting booking object
		Booking booking = this.bookingRepo.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "Booking Id", bookingId));

		booking.setDate(bookingDto.getDate());

		Booking updateBooking = this.bookingRepo.save(booking);

		return this.modelMapper.map(updateBooking, BookingDto.class);
	}

	@Override
	public BookingDto getSingleBooking(Integer bookingId) {
//		getting booking object
		Booking booking = this.bookingRepo.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "Booking Id", bookingId));

		return this.modelMapper.map(booking, BookingDto.class);
	}

}
