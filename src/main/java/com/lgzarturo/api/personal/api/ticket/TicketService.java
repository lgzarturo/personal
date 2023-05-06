package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.generic.CrudService;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;

public interface TicketService extends CrudService<TicketRequest, TicketResponse, Long> {
}
