package com.hotelService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelService.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
