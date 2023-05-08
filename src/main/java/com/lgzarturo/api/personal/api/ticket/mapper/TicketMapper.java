package com.lgzarturo.api.personal.api.ticket.mapper;

import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketResponse mapToResponse(Ticket ticket);
}
