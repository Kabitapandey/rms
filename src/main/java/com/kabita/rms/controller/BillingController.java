package com.kabita.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabita.rms.payload.ApiResponse;
import com.kabita.rms.payload.BillResponse;
import com.kabita.rms.payload.BillingDto;
import com.kabita.rms.repository.BillingRepository;
import com.kabita.rms.servicesImpl.BillingServiceImpl;

@RestController
@RequestMapping("/api")

public class BillingController {
	@Autowired
	BillingServiceImpl billingService;

	@Autowired
	BillingRepository billingRepo;

//	getting bills per user
	@GetMapping("/user/{userId}/bills")
	public ResponseEntity<BillResponse> getBillingByUser(@PathVariable Integer userId) {
		BillResponse billingDto = this.billingService.getBillByUser(userId);

		return new ResponseEntity<BillResponse>(billingDto, HttpStatus.OK);
	}

//deleting of bills
	@DeleteMapping("/bill/{billId}")
	public ResponseEntity<ApiResponse> deleteBill(@PathVariable Integer billId) {
		this.billingService.deleteBilling(billId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Bill deleted successfully", true), HttpStatus.OK);
	}

//updating of bills
	@PutMapping("bill/{billId}")
	public ResponseEntity<BillingDto> updateBill(@RequestBody BillingDto billingDto, @PathVariable Integer billId) {
		this.billingService.updateBilling(billingDto, billId);

		return new ResponseEntity<BillingDto>(billingDto, HttpStatus.OK);
	}
}
