package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.generic.CatalogService;
import com.lgzarturo.api.personal.api.hotel.dto.HotelResponse;

import java.util.Set;

public interface HotelService extends CatalogService<HotelResponse, Long> {
    String FIELD_TO_SORT_BY = "rating";
    Set<HotelResponse> getHotelsByRatingGreaterThan(Integer rating);
    Set<HotelResponse> getHotelsByRatingBetween(Integer rating, Integer rating2);
    Set<HotelResponse> getHotelsByRating(Integer rating);
    Set<HotelResponse> getHotelsByCity(String city);
    Set<HotelResponse> getHotelsByCountry(String country);
}
