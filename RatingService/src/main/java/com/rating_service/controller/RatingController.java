package com.rating_service.controller;

import java.util.List;

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

import com.rating_service.entities.Rating;
import com.rating_service.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	RatingService ratingService;

	@PostMapping
	public ResponseEntity<Rating> savaRating(@RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.saveRatings(rating));
	}

	@GetMapping
	public ResponseEntity<List<Rating>> getALLRatings() {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatings());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Rating> getRatingById(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.findRatingById(id));
	}

	@GetMapping("/hotels/{id}")
	public ResponseEntity<List<Rating>> getRatingUsingHoteId(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.findByHotelId(id));
	}

	@GetMapping("/users/{user_id}")
	public ResponseEntity<List<Rating>> getRatingUsingUserId(@PathVariable("user_id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.findByUserId(id));
	}

	@PutMapping("{id}")
	public ResponseEntity<Rating> updateRatings(@PathVariable int id, @RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.updateRatingById(id, rating));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteRating(@PathVariable int id) {
		try {
			Rating findRatingById = ratingService.findRatingById(id);
			if (findRatingById != null) {
				ratingService.deleteRatingById(id);
				return ResponseEntity.status(HttpStatus.OK).body("Record Successfully deleted");
			} else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating Id not found");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rating Id not found");
		}
	}
}
