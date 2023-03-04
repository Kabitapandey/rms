package com.kabita.rms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kabita.rms.entities.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
	Optional<UserModel> findByEmail(String email);

	@Query(value = "SELECT * FROM user_model where email=?1", nativeQuery = true)
	UserModel getByEmail(String email);
}
