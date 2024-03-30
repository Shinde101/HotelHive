package com.hotelService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelService.entities.Hotel;
import com.hotelService.service.HotelService;

@RestController
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	HotelService hotelService;

	// It is only for example purpose
	@GetMapping
	public ResponseEntity<List<Hotel>> displayAllHotels() {
		return ResponseEntity.ok(hotelService.getAllHotels());

	}
}
