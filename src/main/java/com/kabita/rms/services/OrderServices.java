package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.OrderDto;

public interface OrderServices {
	void deleteOrder(Integer orderId);

	List<OrderDto> getAllOrders();

	List<OrderDto> getOrderByUser(Integer userId);

	OrderDto addOrder(OrderDto orderDto, Integer userId,Integer itemId);

	OrderDto updateOrder(OrderDto orderDto, Integer orderId);

	OrderDto getSingleOrder(Integer orderId);

}
