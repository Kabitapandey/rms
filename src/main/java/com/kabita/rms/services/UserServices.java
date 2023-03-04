package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.UserDto;

public interface UserServices {
UserDto createUser(UserDto userDto);
List<UserDto> getAllUsers();
void deleteUser(Integer userId);
UserDto getSingleUser(Integer userId);
UserDto updateUser(UserDto userDto, Integer userId);
}
