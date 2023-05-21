package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.generic.CrudController;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/v1/tickets")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class TicketController implements CrudController<TicketResponse, TicketRequest, Long> {
    private final TicketService ticketService;

    @PostMapping
    @Override
    public ResponseEntity<TicketResponse> post(@RequestBody @Valid TicketRequest ticketRequest) {
        TicketResponse ticketResponse = ticketService.create(ticketRequest);
        URI location = URI.create(String.format("/api/v1/tickets/%s", ticketResponse.getId()));
        return ResponseEntity.created(location).body(ticketResponse);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<TicketResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<TicketResponse> put(@PathVariable Long id, @RequestBody @Valid TicketRequest ticketRequest) {
        return ResponseEntity.accepted().body(ticketService.update(id, ticketRequest));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<Map<String, BigDecimal>> getFlightPrice(@PathVariable Long id) {
        return ResponseEntity.ok(Collections.singletonMap("flightPrice", ticketService.findPrice(id)));
    }
}
