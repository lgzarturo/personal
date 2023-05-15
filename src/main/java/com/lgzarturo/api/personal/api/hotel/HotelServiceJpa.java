package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.generic.SortType;
import com.lgzarturo.api.personal.api.hotel.dto.HotelResponse;
import com.lgzarturo.api.personal.api.hotel.mapper.HotelMapper;
import com.lgzarturo.api.personal.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class HotelServiceJpa implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> getAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_TO_SORT_BY).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_TO_SORT_BY).descending());
        };
        return hotelRepository.findAll(pageRequest).map(HotelMapper.INSTANCE::mapToResponse);
    }

    @Override
    public HotelResponse get(Long id) {
        return HotelMapper.INSTANCE.mapToResponse(getById(id));
    }

    @Transactional
    @Override
    public void active(Long id) {
        getById(id);
        hotelRepository.activate(id);
    }

    @Transactional
    @Override
    public void inactive(Long id) {
        getById(id);
        hotelRepository.deactivate(id);
    }

    @Override
    public Set<HotelResponse> getHotelsByRatingGreaterThan(Integer rating) {
        return hotelRepository.findAllByRatingGreaterThanEqual(rating).stream()
            .map(HotelMapper.INSTANCE::mapToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> getHotelsByRatingBetween(Integer rating, Integer rating2) {
        return hotelRepository.findAllByRatingBetween(rating, rating2).stream()
            .map(HotelMapper.INSTANCE::mapToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> getHotelsByRating(Integer rating) {
        return hotelRepository.findAllByRating(rating).stream()
            .map(HotelMapper.INSTANCE::mapToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> getHotelsByCity(String city) {
        return hotelRepository.findAllByCity(city).stream()
            .map(HotelMapper.INSTANCE::mapToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> getHotelsByCountry(String country) {
        return hotelRepository.findAllByCountry(country).stream()
            .map(HotelMapper.INSTANCE::mapToResponse)
            .collect(Collectors.toSet());
    }

    private Hotel getById(Long id) {
        return hotelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
    }
}
