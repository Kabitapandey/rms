package com.kabita.rms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.UserModel;
import com.kabita.rms.exception.UserNotFoundException;
import com.kabita.rms.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		loading user from the database;
		UserModel user = this.userRepo.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));

		return user;
	}

}
