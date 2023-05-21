package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.generic.CrudController;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationRequest;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/reservations")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ReservationController implements CrudController<ReservationResponse, ReservationRequest, Long> {
    private final ReservationService reservationService;

    @PostMapping
    @Override
    public ResponseEntity<ReservationResponse> post(@RequestBody @Valid ReservationRequest request) {
        ReservationResponse reservationResponse = reservationService.create(request);
        return ResponseEntity.created(getLocation(reservationResponse.getId())).body(reservationResponse);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ReservationResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ReservationResponse> put(@PathVariable Long id, @RequestBody @Valid ReservationRequest request) {
        return ResponseEntity.accepted().location(getLocation(id)).body(reservationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private URI getLocation(Long id) {
        return URI.create(String.format("/api/v1/reservations/%s", id));
    }
}
