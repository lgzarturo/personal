package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.hotel.HotelRepository;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationRequest;
import com.lgzarturo.api.personal.api.reservation.dto.ReservationResponse;
import com.lgzarturo.api.personal.api.reservation.mapper.ReservationMapper;
import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ReservationServiceTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        customerRepository.deleteAll();
        hotelRepository.deleteAll();
        ReservationMapper reservationMapper = new ReservationMapper();
        reservationService = new ReservationServiceJpa(
            customerRepository,
            hotelRepository,
            reservationMapper,
            reservationRepository
        );
        List<Hotel> hotels = Helpers.getRandomHotels(10);
        hotelRepository.saveAll(hotels);
        List<Customer> customers = Helpers.getRandomCustomers(10);
        customerRepository.saveAll(customers);
    }

    @Test
    void itShouldCreate() {
        // Given
        // When
        var response = createReservation();
        // Then
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
    }

    @Test
    void itShouldThrownExceptionIfClientIdIsNull() {
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.create(
                ReservationRequest.builder()
                    .hotelId(hotelRepository.findAll().iterator().next().getId())
                    .paxNumber(2)
                    .totalNights(4)
                    .build()
            )
        );
    }

    @Test
    void itShouldThrownExceptionIfHotelIdIsNull() {
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.create(
                ReservationRequest.builder()
                    .clientId(customerRepository.findAll().iterator().next().getId())
                    .paxNumber(2)
                    .totalNights(4)
                    .build()
            )
        );
    }

    @Test
    void itShouldThrownExceptionIfPaxNumberIsZeroOrGreaterThan101() {
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.create(
                ReservationRequest.builder()
                    .clientId(customerRepository.findAll().iterator().next().getId())
                    .hotelId(hotelRepository.findAll().iterator().next().getId())
                    .paxNumber(0)
                    .totalNights(4)
                    .build()
            )
        );
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.create(
                ReservationRequest.builder()
                    .clientId(customerRepository.findAll().iterator().next().getId())
                    .hotelId(hotelRepository.findAll().iterator().next().getId())
                    .paxNumber(101)
                    .totalNights(4)
                    .build()
            )
        );
    }

    @Test
    void itShouldThrownExceptionIfTotalNightsIsZeroOrGraterThan60() {
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.create(
                ReservationRequest.builder()
                    .clientId(customerRepository.findAll().iterator().next().getId())
                    .hotelId(hotelRepository.findAll().iterator().next().getId())
                    .paxNumber(2)
                    .totalNights(0)
                    .build()
            )
        );
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.create(
                ReservationRequest.builder()
                    .clientId(customerRepository.findAll().iterator().next().getId())
                    .hotelId(hotelRepository.findAll().iterator().next().getId())
                    .paxNumber(2)
                    .totalNights(61)
                    .build()
            )
        );
    }

    @Test
    void itShouldRead() {
        // Given
        var reservation = createReservation();
        // When
        var response = reservationService.read(reservation.getId());
        // Then
        Assertions.assertNotNull(response);
    }

    @Test
    void itShouldUpdate() {
        // Given
        var clientId = customerRepository.findAll().iterator().next().getId();
        var hotelId = hotelRepository.findAll().iterator().next().getId();
        var reservation = createReservation();
        // When
        var response = reservationService.update(
            reservation.getId(),
            ReservationRequest.builder()
                .clientId(clientId)
                .hotelId(hotelId)
                .paxNumber(4)
                .totalNights(8)
                .build()
        );
        // Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(4, response.getTotalPersons());
        Assertions.assertEquals(8, response.getTotalNights());
    }

    @Test
    void itShouldDeleteById() {
        // Given
        var reservation = createReservation();
        // When
        reservationService.deleteById(reservation.getId());
        // Then
        Assertions.assertThrows(
            RuntimeException.class,
            () -> reservationService.read(reservation.getId())
        );
    }

    private ReservationResponse createReservation() {
        var clientId = customerRepository.findAll().iterator().next().getId();
        var hotelId = hotelRepository.findAll().iterator().next().getId();
        var request = ReservationRequest.builder()
            .clientId(clientId)
            .hotelId(hotelId)
            .paxNumber(2)
            .totalNights(4)
            .build();
        return reservationService.create(request);
    }
}
