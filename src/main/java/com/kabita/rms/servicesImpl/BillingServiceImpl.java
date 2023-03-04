package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Billing;
import com.kabita.rms.entities.Booking;
import com.kabita.rms.entities.Items;
import com.kabita.rms.entities.Orders;
import com.kabita.rms.entities.UserModel;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.BillResponse;
import com.kabita.rms.payload.BillingDto;
import com.kabita.rms.repository.BillingRepository;
import com.kabita.rms.repository.BookingRepository;
import com.kabita.rms.repository.OrderRepository;
import com.kabita.rms.repository.UserRepository;
import com.kabita.rms.services.BillingServices;

@Service
public class BillingServiceImpl implements BillingServices {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BillingRepository billingRepo;

	@Autowired
	BookingRepository bookingRepo;

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	UserRepository userRepo;

	@Override
	public void deleteBilling(Integer billId) {
//		getting billing object and deleting it
		Billing bill = this.billingRepo.findById(billId)
				.orElseThrow(() -> new ResourceNotFoundException("Bill", "Bill Id", billId));

		this.billingRepo.delete(bill);

	}

	@Override
	public BillResponse getBillByUser(Integer userId) {
//		list of billing objects from database
		List<Billing> billing = this.billingRepo.getBillingByUser(userId, "unpaid");
		BillResponse bills = new BillResponse();
		double total = 0;
//converting the type billing to billingDto
		List<BillingDto> billingDto = billing.stream().map((bill) -> this.modelMapper.map(bill, BillingDto.class))
				.collect(Collectors.toList());

//getting the orders amount and finding out the total
		for (int i = 0; i < billing.size(); i++) {
			billingDto.get(i).setQuantity(1);
			if (billingDto.get(i).getOrder() != null) {
				billingDto.get(i).setQuantity(billingDto.get(i).getOrder().getQuantity());

			}

			int quantity = billingDto.get(i).getQuantity();
			total += quantity * billing.get(i).getPrice();
		}

		bills.setBillingDto(billingDto);
		bills.setTotal(total);

		return bills;
	}

	@Override
	public void addBookingBilling(BillingDto billingDto, Integer userId, Integer bookingId) {
		Billing billing = this.modelMapper.map(billingDto, Billing.class);
		Booking booking = this.bookingRepo.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "Booking Id", bookingId));
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		billing.setBooking(booking);
		billing.setUser(user);

		this.billingRepo.save(billing);

	}

	@Override
	public void addOrderBilling(BillingDto billingDto, Integer userId, Integer orderId) {
		Billing billing = this.modelMapper.map(billingDto, Billing.class);
//		getting the order object
		Orders order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));
//		getting the user object
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		billing.setOrder(order);
		billing.setUser(user);

		this.billingRepo.save(billing);
	}

	@Override
	public BillingDto updateBilling(BillingDto billingDto, Integer billId) {
		Billing billing = this.billingRepo.findById(billId)
				.orElseThrow(() -> new ResourceNotFoundException("Bill", "Bill Id", billId));
//if user does not give the value than adding the obtained value
		billing.setPaidStatus(billingDto.getPaidStatus());
		billing.setBooking(billing.getBooking());
		billing.setOrder(billing.getOrder());
		billing.setPrice(billing.getPrice());

		this.billingRepo.save(billing);
		return this.modelMapper.map(billing, BillingDto.class);
	}

}
