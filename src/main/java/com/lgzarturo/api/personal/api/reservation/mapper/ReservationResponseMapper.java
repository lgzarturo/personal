package com.lgzarturo.api.personal.api.reservation.mapper;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationResponseMapper {
    ReservationResponseMapper INSTANCE = Mappers.getMapper(ReservationResponseMapper.class);

    @Mapping(target = "hotelResponse", source = "hotel")
    @Mapping(target = "hotelResponse.address", source = "hotel.hotelAddress.address")
    ReservationResponse mapToResponse(Reservation reservation);
}
