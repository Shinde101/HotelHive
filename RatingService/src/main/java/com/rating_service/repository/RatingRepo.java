package com.rating_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rating_service.entities.Rating;

public interface RatingRepo extends JpaRepository<Rating, Integer> {

	public List<Rating> findByHotelId(String id);

	public List<Rating> findByUserId(String id);
}
