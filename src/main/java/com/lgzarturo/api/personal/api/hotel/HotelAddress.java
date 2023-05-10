package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.generic.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="hotels_address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelAddress {
    @Id
    @Column(name = "hotel_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Embedded
    private Address address;
}
