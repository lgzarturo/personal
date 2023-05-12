package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.generic.CrudService;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationRequest;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;

public interface ReservationService extends CrudService<ReservationRequest, ReservationResponse, Long> {
}
