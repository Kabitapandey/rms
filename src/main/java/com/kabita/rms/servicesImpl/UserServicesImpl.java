package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kabita.rms.config.AppConstants;
import com.kabita.rms.entities.Role;
import com.kabita.rms.entities.UserModel;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.UserDto;
import com.kabita.rms.repository.RoleRepository;
import com.kabita.rms.repository.UserRepository;
import com.kabita.rms.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {
	@Autowired
	UserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	RoleRepository roleRepo;

//adding user
	@Override
	public UserDto createUser(UserDto userDto) {
//		checking if email exists or not
		UserModel userModel = this.userRepo.getByEmail(userDto.getEmail());
		if (userModel != null) {
			return null;
		}
		UserModel user = this.modelMapper.map(userDto, UserModel.class);
//		encoding the password 
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//adding default role of user as normal user
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER_ID).orElseThrow();

		user.getRoles().add(role);

		UserModel newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
//		retrieving list of users
		List<UserModel> userModel = this.userRepo.findAll();
//retrieving roles to add to the role object
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER_ID).orElseThrow();

		for (UserModel user : userModel) {
			user.getRoles().add(role);
		}

		return userModel.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
	}

//deleting user
	@Override
	public void deleteUser(Integer userId) {
		UserModel userModel = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		this.userRepo.delete(userModel);
	}

//retrieving single user
	@Override
	public UserDto getSingleUser(Integer userId) {
		UserModel userModel = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		return this.modelMapper.map(userModel, UserDto.class);
	}

//updating user
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		UserModel userModel = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
//		updating user data if the user email does not exist
		Optional<UserModel> user = this.userRepo.findByEmail(userDto.getEmail());
		System.out.println(userDto.getEmail());
		System.out.println(userModel.getEmail());
		if (userDto.getEmail().equalsIgnoreCase(userModel.getEmail())) {
			System.out.println(true);
			userModel.setEmail(userDto.getEmail());
			userModel.setFullName(userDto.getFullName());
			userModel.setPassword(userDto.getPassword());
		} else {
			if (user != null) {
				return null;
			}
			userModel.setEmail(userDto.getEmail());
			userModel.setFullName(userDto.getFullName());
			userModel.setPassword(userDto.getPassword());

		}

		UserModel updatedUserModel = this.userRepo.save(userModel);
		return this.modelMapper.map(updatedUserModel, UserDto.class);
	}

}
