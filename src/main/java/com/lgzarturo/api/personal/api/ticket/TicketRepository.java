package com.lgzarturo.api.personal.api.ticket;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Query("SELECT t FROM tickets t JOIN FETCH t.flight JOIN FETCH t.customer WHERE t.id = :id")
    Optional<Ticket> getTicketByIdWithFlightAndCustomer(@NotNull Long id);
}
