package com.rating_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rating_service.entities.Rating;
import com.rating_service.excetion.ResourceNotFoundException;
import com.rating_service.repository.RatingRepo;
import com.rating_service.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	RatingRepo ratingRepository;

	@Override
	public Rating saveRatings(Rating rating) {
		// TODO Auto-generated method stub
		return ratingRepository.save(rating);
	}

	@Override
	public Rating findRatingById(int id) {
		// TODO Auto-generated method stub
		return ratingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Given id related record is not available"));
	}

	@Override
	public List<Rating> getRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public List<Rating> findByHotelId(String id) {
		// TODO Auto-generated method stub
		return ratingRepository.findByHotelId(id);
	}

	@Override
	public List<Rating> findByUserId(String id) {
		// TODO Auto-generated method stub
		return ratingRepository.findByUserId(id);
	}

	@Override
	public Rating updateRatingById(int id, Rating rating) {

		Rating existingUser = ratingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found"));

		if (existingUser != null) {
			existingUser.setUserId(rating.getUserId());
			existingUser.setFeedback(rating.getFeedback());
			existingUser.setHotelId(rating.getHotelId());
			existingUser.setRating(rating.getRating());
		}

		return ratingRepository.save(existingUser);
	}

	@Override
	public void deleteRatingById(int id) {
		ratingRepository.deleteById(id);
	}
}
