package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.generic.CrudService;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;

import java.math.BigDecimal;

public interface TicketService extends CrudService<TicketRequest, TicketResponse, Long> {
    BigDecimal findPrice(Long flightId);
}
