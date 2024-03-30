package com.user_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

	private int id;
	private String userId;
	private Long hotelId;
	private int rating;
	private String feedback;
	private Hotel hotel;
}
