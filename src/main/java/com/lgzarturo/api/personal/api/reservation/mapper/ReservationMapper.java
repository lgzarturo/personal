package com.lgzarturo.api.personal.api.reservation.mapper;

import com.lgzarturo.api.personal.api.hotel.dto.HotelAddressResponse;
import com.lgzarturo.api.personal.api.hotel.dto.HotelResponse;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReservationMapper implements Function<Reservation, ReservationResponse> {
    @Override
    public ReservationResponse apply(Reservation reservation) {
        HotelAddressResponse addressResponse = new HotelAddressResponse();
        BeanUtils.copyProperties(reservation.getHotel().getHotelAddress().getAddress(), addressResponse);
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(reservation.getHotel(), hotelResponse);
        hotelResponse.setId(reservation.getHotel().getId());
        hotelResponse.setAddress(addressResponse);
        ReservationResponse reservationResponse = new ReservationResponse();
        BeanUtils.copyProperties(reservation, reservationResponse);
        reservationResponse.setId(reservation.getId());
        reservationResponse.setHotelResponse(hotelResponse);
        return reservationResponse;
    }
}
