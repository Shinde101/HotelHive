package com.user_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user_service.entities.User;
import com.user_service.exception.ResourceNotFoundException;
import com.user_service.repositories.UserRepository;
import com.user_service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
//		String id = UUID.randomUUID().toString();
//		user.setId(id);

		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {

		return userRepository.findAll();
	}

	@Override
	public User getUser(String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id is not found"));
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public String deleteUser(String id) {

		if (id != null) {
			userRepository.deleteById(id);
			return "Record deleted...";
		} else
			return "Given id is not available";
	}
}
