package com.lgzarturo.api.personal.api.profile;

import com.lgzarturo.api.personal.api.generic.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="profiles_address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileAddress {
    @Id
    @Column(name = "profile_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @Embedded
    private Address address;
}
