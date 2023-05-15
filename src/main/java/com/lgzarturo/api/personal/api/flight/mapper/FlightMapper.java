package com.lgzarturo.api.personal.api.flight.mapper;

import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.dto.FlightRequest;
import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    FlightResponse mapToResponse(Flight flight);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Flight mapToEntity(FlightRequest request);
}
