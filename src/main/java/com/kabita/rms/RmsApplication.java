package com.kabita.rms;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kabita.rms.config.AppConstants;
import com.kabita.rms.entities.Role;
import com.kabita.rms.entities.UserModel;
import com.kabita.rms.repository.RoleRepository;
import com.kabita.rms.repository.UserRepository;

import java.util.List;

@SpringBootApplication
public class RmsApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepo;

	@Autowired
	UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(RmsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	password encrypter
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
//adding value to the roles table if the table is empty
	@Override
	public void run(String... args) throws Exception {
		Role role = new Role();
		role.setRoleId(AppConstants.NORMAL_USER_ID);
		role.setName("ROLE_USER");

		Role r2 = new Role();
		r2.setRoleId(502);
		r2.setName("ROLE_ADMIN");

		List<Role> roles = List.of(role, r2);

		roleRepo.saveAll(roles);
	}
}
