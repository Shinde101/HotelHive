package com.rating_service.services;

import java.util.List;

import com.rating_service.entities.Rating;

public interface RatingService {

	public Rating saveRatings(Rating rating);

	public List<Rating> getRatings();

	public Rating findRatingById(int id);

	public List<Rating> findByHotelId(String id);

	public List<Rating> findByUserId(String id);

	public Rating updateRatingById(int id, Rating rating);

	public void deleteRatingById(int id);
}
