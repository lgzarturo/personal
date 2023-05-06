package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tickets")
@AllArgsConstructor
@Slf4j
public class TicketController {
    private final TicketService ticketService;

    // TODO
    //  1. Crear métodos para dar de alta clientes y vuelos aleatorios.
    //  2. Crear clientes y vuelos de prueba al iniciar la aplicación.
    //  3. Realizar pruebas con el endpoint POST de tickets.
    //  4. Modificar las pruebas con datos aleatorios.

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody @Valid TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.create(ticketRequest));
    }
}
