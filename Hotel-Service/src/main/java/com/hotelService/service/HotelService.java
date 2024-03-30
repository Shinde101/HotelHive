package com.hotelService.service;

import java.util.List;

import com.hotelService.entities.Hotel;

public interface HotelService {
	public Hotel saveHotel(Hotel hotel);

	public Hotel getHotelById(long id);

	public List<Hotel> getAllHotels();

	public void deleteHotel();

	public void deleteHotelById(long id);

	public Hotel updateHotel(Hotel hotel);

}