package com.lgzarturo.api.personal.utils;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.lgzarturo.api.personal.api.address.Address;
import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.flight.Airline;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.tour.Tour;
import com.lgzarturo.api.personal.api.user.Role;
import com.lgzarturo.api.personal.api.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Helpers {

    private static final List<String> airports = List.of(
        "MEX", "CUN", "TIJ", "MTY", "GDL", "CJS", "CUL",
        "HMO", "LAP", "PVR", "SJD", "VER", "ZCL", "ZIH", "ACA", "BJX",
        "CME", "CPE", "CTM", "CUU", "GUB", "HUX", "LMM", "LTO", "MAM",
        "MID", "MLM", "MTT", "MXL", "NLD", "OAX", "PAZ", "PBC", "PXM",
        "QRO", "REX", "SJD", "SLP", "TAM", "TAP", "TGZ", "TPQ", "TRC",
        "UPN", "VSA", "VSA", "ZIH", "ZLO"
    );

    public static String generateEmail() {
        var faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
    }

    public static User getRandomUser(String encodedPassword) {
        return getRandomUserWithRoles(encodedPassword, List.of(Role.ROLE_USER));
    }

    public static User getRandomUserWithRoles(String encodedPassword, List<Role> roles) {
        return User.builder()
            .email(generateEmail())
            .password(encodedPassword)
            .isActive(true)
            .role(roles)
            .build();
    }

    public static User getAdminUser(String email, String encodedPassword) {
        return User.builder()
            .email(email)
            .password(encodedPassword)
            .isActive(true)
            .role(List.of(Role.ROLE_ADMIN))
            .build();
    }

    public static List<User> getRandomUsers(int count, String encodedPassword) {
        return Stream.of(new User[count]).map(user -> getRandomUser(encodedPassword)).toList();
    }

    public static Customer.CustomerBuilder getCustomerBuilder() {
        var faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.EXPIRATION_DATE_FORMAT);
        return Customer.builder()
            .fullName(firstName + " " + lastName)
            .creditCardNumber(faker.finance().creditCard(CreditCardType.MASTERCARD))
            .creditCardExpirationDate(LocalDate.now().plusYears(3).format(formatter))
            .phoneNumber(faker.phoneNumber().phoneNumber());
    }

    public static Customer getRandomCustomer() {
        return getCustomerBuilder().build();
    }

    public static Customer getUserWithTicketReservationAndTour(Ticket ticket, Reservation reservation, Tour tour) {
        var customer = getCustomerBuilder();
        customer.totalReservations(1)
            .totalFlights(20)
            .totalLodgings(15)
            .totalTickets(1)
            .totalTours(1)
            .tickets(Set.of(ticket))
            .reservations(Set.of(reservation))
            .tours(Set.of(tour));
        return customer.build();
    }

    public static Hotel.HotelBuilder getHotelBuilder() {
        var faker = new Faker();
        double value = Double.parseDouble(faker.commerce().price(500.00, 20000.00));
        return Hotel.builder()
            .name(faker.company().name())
            .rating(faker.random().nextInt(1, 5))
            .minimumPrice(BigDecimal.valueOf(value))
            .maximumPrice(BigDecimal.valueOf(value + (value * 0.25)));
    }

    public static Hotel getRandomHotel() {
        return getHotelBuilder().build();
    }

    public static Address getRandomAddress() {
        var faker = new Faker();
        return Address.builder()
            .street(faker.address().streetAddress())
            .city(faker.address().city())
            .state(faker.address().state())
            .country(faker.address().country())
            .zipCode(faker.address().zipCode())
            .latitude(Double.parseDouble(faker.address().latitude()))
            .longitude(Double.parseDouble(faker.address().latitude()))
            .build();
    }

    public static Tour.TourBuilder getTourBuilder() {
        var faker = new Faker();
        return Tour.builder()
            .name(faker.company().name())
            .description(faker.lorem().paragraph())
            .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price(100.00, 2000.00))));
    }

    public static Tour getRandomTour() {
        return getTourBuilder().build();
    }

    public static Tour getTourWithCustomer(Customer customer) {
        return getTourBuilder()
            .customer(customer)
            .build();
    }

    public static Ticket.TicketBuilder getTicketBuilder() {
        var faker = new Faker();
        int start = faker.random().nextInt(10);
        int end = start + faker.random().nextInt(10);
        if (start == end) {
            end += 1;
        }
        return Ticket.builder()
            .arrivalDate(LocalDateTime.now().plusDays(start))
            .departureDate(LocalDateTime.now().plusDays(end))
            .purchaseDate(LocalDateTime.now())
            .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price(1000.00, 30000.00))));
    }

    public static Ticket getRandomTicket(Tour tour) {
        var ticket = getTicketBuilder();
        if (tour != null) {
            ticket.tour(tour);
        }
        return ticket.build();
    }

    public static Ticket getTicketWithFlightAndCustomer(Flight flight, Customer customer) {
        return getTicketBuilder()
            .flight(flight)
            .customer(customer)
            .build();
    }

    public static Flight.FlightBuilder getFlightBuilder() {
        var faker = new Faker();
        String destination = airports.get(faker.random().nextInt(airports.size()));
        return Flight.builder()
            .airline(Airline.values()[faker.random().nextInt(Airline.values().length)])
            .flightNumber(faker.code().gtin8())
            .originName(destination)
            .destinationName(airports.stream()
                .filter(airport -> !airport.equals(destination))
                .toList().get(faker.random().nextInt(airports.size()-1)))
            .originLatitude(0.0)
            .originLongitude(0.0)
            .destinationLatitude(0.0)
            .destinationLongitude(0.0)
            .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price(1000.00, 30000.00))));
    }

    public static Flight getRandomFlight() {
        return getFlightBuilder().build();
    }

    public static Reservation.ReservationBuilder getReservationBuilder() {
        var faker = new Faker();
        int start = faker.random().nextInt(5);
        int end = start + faker.random().nextInt(20);
        if (start == end) {
            end += 1;
        }
        int nights = end - start;
        return Reservation.builder()
            .dateReservation(LocalDateTime.now())
            .dateCheckIn(LocalDate.now().plusDays(start))
            .dateCheckOut(LocalDate.now().plusDays(end))
            .totalPersons(faker.random().nextInt(5))
            .totalNights(nights)
            .totalAmount(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price(1000.00, 30000.00))));
    }

    public static Reservation getRandomReservation(Hotel hotel) {
        var reservation = getReservationBuilder();
        if (hotel != null) {
            reservation.hotel(hotel);
        }
        return reservation.build();
    }

    public static Reservation getReservationWithHotelAndCustomer(Hotel hotel, Customer customer) {
        return getReservationBuilder()
            .hotel(hotel)
            .customer(customer)
            .build();
    }
}
