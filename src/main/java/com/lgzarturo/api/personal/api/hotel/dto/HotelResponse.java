package com.lgzarturo.api.personal.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private Integer rating;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    private HotelAddressResponse address;
}
