package com.kabita.rms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabita.rms.payload.ApiResponse;
import com.kabita.rms.payload.OrderDto;
import com.kabita.rms.services.OrderServices;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	OrderServices orderService;

	@PostMapping("/user/{userId}/product/{productId}/order")
	public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable Integer userId,
			@PathVariable Integer productId) {
		OrderDto order = this.orderService.addOrder(orderDto, userId, productId);

		if (order == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Item out of stock", false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<OrderDto>(order, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDto>> getAllOrders() {
		return new ResponseEntity<List<OrderDto>>(this.orderService.getAllOrders(), HttpStatus.OK);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderDto> getSingleItem(@PathVariable Integer orderId) {
		return new ResponseEntity<OrderDto>(this.orderService.getSingleOrder(orderId), HttpStatus.OK);
	}

	@PutMapping("/order/{orderId}")
	public ResponseEntity<OrderDto> updateProduct(@Valid @RequestBody OrderDto orderDto,
			@PathVariable Integer orderId) {
		return new ResponseEntity<OrderDto>(this.orderService.updateOrder(orderDto, orderId), HttpStatus.OK);
	}

	@DeleteMapping("/order/{orderId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer orderId) {
		this.orderService.deleteOrder(orderId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order deleted successfully!", true), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/order")
	public ResponseEntity<List<OrderDto>> getAllCart(@PathVariable Integer userId) {

		return new ResponseEntity<List<OrderDto>>(this.orderService.getOrderByUser(userId), HttpStatus.OK);
	}

}
