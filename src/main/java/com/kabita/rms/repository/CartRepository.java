package com.kabita.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kabita.rms.entities.Cart;
import com.kabita.rms.entities.UserModel;

public interface CartRepository extends JpaRepository<Cart, Integer> {
//	getting cart according to user
	List<Cart> getCartByUser(UserModel userId);

//	query to get cart value according to user id and product
	@Query(value = "select * from cart where user_id=?1 and product_id=?2", nativeQuery = true)
	public Cart getCartByUserAndProduct(Integer userId, Integer productId);
}
