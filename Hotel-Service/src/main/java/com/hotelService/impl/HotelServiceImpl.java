package com.hotelService.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelService.entities.Hotel;
import com.hotelService.exception.ResourceNotFoundException;
import com.hotelService.repository.HotelRepository;
import com.hotelService.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	HotelRepository hotelRepository;

	@Override
	public Hotel saveHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		return hotelRepository.save(hotel);
	}

	@Override
	public Hotel getHotelById(long id) {

		return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel id doesn't found"));
	}

	@Override
	public List<Hotel> getAllHotels() {
		// TODO Auto-generated method stub
		return hotelRepository.findAll();
	}

	@Override
	public void deleteHotelById(long id) {

		hotelRepository.deleteById(id);
	}

	@Override
	public void deleteHotel() {

		hotelRepository.deleteAll();
	}

	@Override
	public Hotel updateHotel(Hotel hotel) {
		// TODO Auto-generated method stub

		return hotelRepository.save(hotel);

	}

}
