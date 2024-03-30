package com.user_service.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.user_service.entities.Hotel;
import com.user_service.entities.Rating;
import com.user_service.entities.User;
import com.user_service.external.services.HotelService;
import com.user_service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	HotelService hotelService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {

		String id = UUID.randomUUID().toString();
		user.setId(id);
		User saveUser = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(saveUser));
	}

	int retryCount = 1;

	@GetMapping("/{id}")
	 @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod =
	 "ratingHotelFallBack")
	@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable String id) {

		logger.info("get single user controller handler: UserController");
		logger.info("Retry count: {}", retryCount);
		retryCount++;
		User user = userService.getUser(id);
		
		if (user != null) {
			Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getId(),
					Rating[].class);

			List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

			List<Rating> ratingList = ratings.stream().map(rating -> {

				// get the data using RestTemplate
//				ResponseEntity<Hotel> forEntity = restTemplate
//						.getForEntity("http://HOTEL-SERVICE/hotel/" + rating.getHotelId(), Hotel.class);
//				Hotel hotel = forEntity.getBody();

				// get the data using Feign client
				Hotel hotel = hotelService.getHotelById(rating.getHotelId());

				rating.setHotel(hotel);
				return rating;
			}).collect(Collectors.toList());

			user.setRatings(ratingList);
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	public ResponseEntity<User> ratingHotelFallBack(String id, Exception e) {
		System.out.println("fallBack method is executed because service is down" + e.getMessage());
		User user = User.builder().email("dummy@gmail.com").name("dummy")
				.about("fallBack method is executed because server is down").build();
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {

		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {

		User existingUser = null;
		try {
			existingUser = userService.getUser(id);

			if (existingUser != null) {
				existingUser.setId(id);
				existingUser.setName(user.getName());
				existingUser.setEmail(user.getEmail());
				existingUser.setAbout(user.getAbout());

				userService.updateUser(existingUser);
			}

		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("User with given id is not available");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
		}

		return ResponseEntity.ok(existingUser);
	}

	@DeleteMapping("{id}")
	public String deleteUser(@PathVariable String id) {

		User user = null;
		try {
			user = userService.getUser(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user != null) {
			try {
				userService.deleteUser(id);
				return "Record deleted Successfully";
			} catch (Exception e) {
				e.printStackTrace();
				return "Exception Occurs During User Deletion";
			}

		} else {
			return "User id is not Available";

		}

	}

}