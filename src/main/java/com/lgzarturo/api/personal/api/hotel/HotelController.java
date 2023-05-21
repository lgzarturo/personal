package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.generic.CatalogController;
import com.lgzarturo.api.personal.api.generic.SortType;
import com.lgzarturo.api.personal.api.hotel.dto.HotelResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("api/v1/hotels")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class HotelController implements CatalogController<HotelResponse, Long> {

    private final HotelService hotelService;

    @GetMapping
    @Override
    public ResponseEntity<Page<HotelResponse>> getAll(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) SortType sortType) {
        if (Objects.isNull(page) || page <= 0) page = 1;
        if (Objects.isNull(size) || size <= 0) size = 10;
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = hotelService.getAll(page, size, sortType);
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Set<HotelResponse>> getHotelsByRating(
        @PathVariable Integer rating,
        @RequestParam(required = false) FilterType filterType) {
        if (Objects.isNull(filterType)) filterType = FilterType.NONE;
        var response = switch (filterType) {
            case GREATER_THAN_RATING -> hotelService.getHotelsByRatingGreaterThan(rating);
            case BETWEEN_RATING -> hotelService.getHotelsByRatingBetween(rating, rating + 1);
            default -> hotelService.getHotelsByRating(rating);
        };
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<Set<HotelResponse>> getHotelsByCity(@PathVariable String city) {
        var response = hotelService.getHotelsByCity(city);
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<Set<HotelResponse>> getHotelsByCountry(@PathVariable String country) {
        var response = hotelService.getHotelsByCountry(country);
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<HotelResponse> get(@PathVariable  Long id) {
        return ResponseEntity.ok(hotelService.get(id));
    }

    @PatchMapping("/{id}/active")
    @Override
    public ResponseEntity<Void> active(@PathVariable Long id) {
        hotelService.active(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/{id}/inactive")
    @Override
    public ResponseEntity<Void> inactive(@PathVariable Long id) {
        hotelService.inactive(id);
        return ResponseEntity.accepted().build();
    }
}
