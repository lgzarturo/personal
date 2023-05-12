package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.hotel.HotelRepository;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationRequest;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
import com.lgzarturo.api.personal.api.reservation.mapper.ReservationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Transactional
@Service
@Slf4j
public class ReservationServiceJpa implements ReservationService {

    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow();
        Customer customer = customerRepository.findById(request.getClientId()).orElseThrow();
        boolean randomBoolean = Math.random() < 0.5;
        BigDecimal pickedPrice = randomBoolean ? hotel.getMinimumPrice() : hotel.getMaximumPrice();
        Reservation reservation = Reservation.builder()
            .hotel(hotel)
            .customer(customer)
            .totalNights(request.getTotalNights())
            .totalPersons(request.getTotalPersons())
            .dateReservation(LocalDateTime.now())
            .dateCheckIn(LocalDate.now().plusDays(1))
            .dateCheckOut(LocalDate.now().plusDays(request.getTotalNights()))
            .totalAmount(pickedPrice.multiply(BigDecimal.valueOf(request.getTotalNights())))
            .build();
        return reservationMapper.apply(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse read(Long id) {
        Reservation reservation = getById(id);
        return reservationMapper.apply(reservation);
    }

    @Override
    public ReservationResponse update(Long id, ReservationRequest request) {
        Reservation reservation = getById(id);
        return reservationMapper.apply(reservation);
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = getById(id);
        reservationRepository.deleteById(id);
    }

    private Reservation getById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }
}
