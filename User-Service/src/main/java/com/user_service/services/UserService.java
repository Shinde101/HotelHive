package com.user_service.services;

import java.util.List;

import com.user_service.entities.User;

public interface UserService {

	User saveUser(User user);

	List<User> getAllUser();

	User getUser(String id);

	User updateUser(User user);

	String deleteUser(String id);

}