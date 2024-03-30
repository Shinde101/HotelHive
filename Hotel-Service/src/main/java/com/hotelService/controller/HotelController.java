package com.hotelService.controller;

import java.util.List;

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

import com.hotelService.entities.Hotel;
import com.hotelService.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	private final Logger logger = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	HotelService hotelService;

	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {

		Hotel hotelDetails = hotelService.saveHotel(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotelDetails));

	}

	@GetMapping("/{hotel_id}")
	public ResponseEntity<Hotel> getHotelById(@PathVariable("hotel_id") Long id) {

		Hotel hotelById = null;
		try {
			hotelById = hotelService.getHotelById(id);
			System.out.println("hotelById " + hotelById);
			if (hotelById != null) {
				return ResponseEntity.status(HttpStatus.OK).body(hotelById);
			}

		} catch (Exception e) {
			logger.info("Record not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping
	public ResponseEntity<List<Hotel>> displayAllHotels() {
		return ResponseEntity.ok(hotelService.getAllHotels());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRecordById(@PathVariable Long id) {

		try {
			hotelService.deleteHotelById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Given id is not available ");
		}

		return ResponseEntity.ok().body("Record Deleted Successfully");

	}

	@DeleteMapping
	public ResponseEntity<String> deleteRecords() {

		hotelService.deleteHotel();

		return ResponseEntity.ok().body("All Records Deleted from hotel table");

	}

	@PutMapping("/{id}")
	public ResponseEntity<Hotel> updateHotelDetails(@PathVariable Long id, @RequestBody Hotel hotel) {

		Hotel existingHotel = null;
		try {
			existingHotel = hotelService.getHotelById(id);
			if (existingHotel != null) {
				existingHotel.setName(hotel.getName());
				existingHotel.setLocation(hotel.getLocation());
				existingHotel.setAbout(hotel.getAbout());

				hotelService.updateHotel(existingHotel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Given id is not available");
		}

		return ResponseEntity.ok(existingHotel);

	}

}
