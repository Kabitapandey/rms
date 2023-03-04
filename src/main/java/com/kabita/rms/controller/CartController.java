package com.kabita.rms.controller;

import java.util.List;

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
import com.kabita.rms.payload.CartDto;
import com.kabita.rms.servicesImpl.CartServiceImpl;

@RestController
@RequestMapping("/api")
public class CartController {
	@Autowired
	CartServiceImpl cartService;

	@PostMapping("/user/{userId}/product/{productId}/cart")
	public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto, @PathVariable Integer productId,
			@PathVariable Integer userId) {
		return new ResponseEntity<>(this.cartService.addToCart(cartDto, userId, productId), HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/cart")
	public ResponseEntity<List<CartDto>> getAllCart(@PathVariable Integer userId){
		
		return new ResponseEntity<List<CartDto>>(this.cartService.getAllCart(userId),HttpStatus.OK);
	}
	
	@GetMapping("/cart/{cartId}")
	public ResponseEntity<CartDto> getSingleCart(@PathVariable Integer cartId){
		return new ResponseEntity<CartDto>(this.cartService.getSingleCart(cartId),HttpStatus.OK);
	}
	
	@PutMapping("/cart/{cartId}")
	public ResponseEntity<CartDto> updateCart(@RequestBody CartDto cartDto, @PathVariable Integer cartId){
		return new ResponseEntity<CartDto>(this.cartService.updateCart(cartDto, cartId),HttpStatus.OK);
	}
	
	@DeleteMapping("/cart/{cartId}")
	public ResponseEntity<ApiResponse> deleteCart(@PathVariable Integer cartId){
		this.cartService.deleteCart(cartId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Cart item deleted successfully", true),HttpStatus.OK);
	}
}
