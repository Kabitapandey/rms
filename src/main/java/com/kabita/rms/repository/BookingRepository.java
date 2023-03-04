package com.kabita.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabita.rms.entities.Booking;
import com.kabita.rms.entities.UserModel;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
//	getting booking data as per user
	List<Booking> getBookingsByUser(UserModel user);
}
