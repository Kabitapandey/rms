package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Items;
import com.kabita.rms.entities.Orders;
import com.kabita.rms.entities.UserModel;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.BillingDto;
import com.kabita.rms.payload.OrderDto;
import com.kabita.rms.repository.BillingRepository;
import com.kabita.rms.repository.ItemsRepository;
import com.kabita.rms.repository.OrderRepository;
import com.kabita.rms.repository.UserRepository;
import com.kabita.rms.services.OrderServices;

@Service
public class OrderServiceImpl implements OrderServices {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ItemsRepository itemRepo;

	@Autowired
	BillingServiceImpl billingService;

	@Override
	public void deleteOrder(Integer orderId) {
		Orders order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		this.orderRepo.delete(order);
	}

	@Override
	public List<OrderDto> getAllOrders() {
		List<Orders> orders = this.orderRepo.findAll();
		return orders.stream().map((order) -> this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<OrderDto> getOrderByUser(Integer userId) {
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Orders> orders = this.orderRepo.getOrderByUser(user);
		
		return orders.stream().map((order) -> this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public OrderDto addOrder(OrderDto orderDto, Integer userId, Integer itemId) {
		Orders order = this.modelMapper.map(orderDto, Orders.class);
		Items item = this.itemRepo.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", itemId));
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		order.setUser(user);
		order.setItems(item);
		order.setQuantity(orderDto.getQuantity());

		BillingDto billingDto = new BillingDto();
		billingDto.setPaidStatus("unpaid");
		billingDto.setPrice(item.getPrice());
//adding order if the item is available
		if (item.getStock() > 0 && item.getStock()>=order.getQuantity()) {
			Orders addOrder = this.orderRepo.save(order);
			int orderId = order.getOrderId();
			billingService.addOrderBilling(billingDto, userId, orderId);
//			decreasing the quantity as the user orders item
			item.setStock(item.getStock() - order.getQuantity());

			itemRepo.save(item);

			return this.modelMapper.map(addOrder, OrderDto.class);
		}
		return null;
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, Integer orderId) {
		Orders order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		order.setAddress(orderDto.getAddress());
		order.setPhoneNo(orderDto.getPhoneNo());
		
		
		Orders updatedOrder = this.orderRepo.save(order);

		return this.modelMapper.map(updatedOrder, OrderDto.class);
	}

	@Override
	public OrderDto getSingleOrder(Integer orderId) {
		Orders order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		return this.modelMapper.map(order, OrderDto.class);
	}

}
