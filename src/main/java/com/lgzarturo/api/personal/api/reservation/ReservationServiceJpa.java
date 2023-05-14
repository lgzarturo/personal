package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.hotel.HotelRepository;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationRequest;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
import com.lgzarturo.api.personal.api.reservation.mapper.ReservationMapper;
import com.lgzarturo.api.personal.api.reservation.mapper.ReservationResponseMapper;
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

    private static final BigDecimal tax_price_percentage = BigDecimal.valueOf(0.16);
    private static final BigDecimal margin_price_percentage = BigDecimal.valueOf(0.10);
    private static final BigDecimal hospitality_price_percentage = BigDecimal.valueOf(0.05);

    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        if (request.getClientId() == null || request.getHotelId() == null) {
            throw new IllegalArgumentException("Client and Hotel are required");
        }
        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow();
        Customer customer = customerRepository.findById(request.getClientId()).orElseThrow();
        Reservation reservation = Reservation.builder()
            .hotel(hotel)
            .customer(customer)
            .totalNights(request.getTotalNights())
            .totalPersons(request.getPaxNumber())
            .dateReservation(LocalDateTime.now())
            .dateCheckIn(LocalDate.now().plusDays(1))
            .dateCheckOut(LocalDate.now().plusDays(request.getTotalNights()))
            .totalAmount(getTotalPrice(hotel, request.getTotalNights()))
            .build();
        return reservationMapper.apply(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse read(Long id) {
        Reservation reservation = getById(id);
        return ReservationResponseMapper.INSTANCE.mapToResponse(reservation);
    }

    @Override
    public ReservationResponse update(Long id, ReservationRequest request) {
        Hotel hotel = null;
        if (request.getHotelId() != null) {
            hotel = hotelRepository.findById(request.getHotelId()).orElseThrow();
        }
        Reservation reservation = getById(id);
        reservation.setTotalNights(request.getTotalNights());
        reservation.setTotalPersons(request.getPaxNumber());
        if (hotel != null) {
            hotel.addReservation(reservation);
        }
        reservation.setTotalAmount(getTotalPrice(reservation.getHotel(), request.getTotalNights()));
        return ReservationResponseMapper.INSTANCE.mapToResponse(reservationRepository.save(reservation));
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = getById(id);
        reservationRepository.delete(reservation);
    }

    private Reservation getById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }


    private BigDecimal getTotalPrice(Hotel hotel, int totalNights) {
        boolean randomBoolean = Math.random() < 0.5;
        BigDecimal sumTaxesAndMargin = BigDecimal.valueOf(1)
            .add(tax_price_percentage)
            .add(margin_price_percentage)
            .add(hospitality_price_percentage);
        BigDecimal pickedPrice = randomBoolean ? hotel.getMinimumPrice() : hotel.getMaximumPrice();
        log.info("pickedPrice: {}, <- {}", pickedPrice, randomBoolean ? "minimumPrice": "maximumPrice");
        return pickedPrice.multiply(BigDecimal.valueOf(totalNights)).multiply(sumTaxesAndMargin);
    }
}
