package com.lgzarturo.api.personal.api.reservation;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Optional<Reservation> findByUuid(UUID uuid);
}
