package com.kabita.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kabita.rms.entities.Billing;

public interface BillingRepository extends JpaRepository<Billing, Integer> {
//jpa query to get the bills which are unpaid
	@Query(value = "select * from billing where user_user_id=?1 AND paid_status=?2", nativeQuery = true)
	List<Billing> getBillingByUser(Integer userId, String status);
}
