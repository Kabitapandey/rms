package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.CartDto;

public interface CartServices {
	CartDto addToCart(CartDto cartDto, Integer userId,Integer productId);
	List<CartDto> getAllCart(Integer userId);
	CartDto updateCart(CartDto cartDto,Integer cartId);
	CartDto getSingleCart(Integer cartId);
	void deleteCart(Integer cartId);
}
