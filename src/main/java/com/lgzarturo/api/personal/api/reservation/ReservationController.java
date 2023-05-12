package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.generic.CrudController;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationRequest;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
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
public class ReservationController implements CrudController<ReservationResponse, ReservationRequest, Long> {
    private final ReservationService reservationService;

    @PostMapping
    @Override
    public ResponseEntity<ReservationResponse> post(@RequestBody @Valid ReservationRequest request) {
        ReservationResponse reservationResponse = reservationService.create(request);
        URI location = URI.create(String.format("/api/v1/reservations/%s", reservationResponse.getId()));
        return ResponseEntity.created(location).body(reservationResponse);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ReservationResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ReservationResponse> put(@PathVariable Long id, @RequestBody @Valid ReservationRequest request) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return null;
    }
}
