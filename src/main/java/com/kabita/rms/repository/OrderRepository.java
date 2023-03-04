package com.kabita.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabita.rms.entities.Orders;
import com.kabita.rms.entities.UserModel;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
//	getting orders as per the user
	List<Orders> getOrderByUser(UserModel user);
}
