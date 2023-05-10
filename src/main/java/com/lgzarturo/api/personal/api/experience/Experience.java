package com.lgzarturo.api.personal.api.experience;

import com.lgzarturo.api.personal.api.profile.Profile;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity(name="experiences")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Experience extends AbstractAuditable<User, Long> {
    @Column(length = 80)
    private String name;
    @Column(length = 160)
    private String description;
    private String url;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ExperienceType type;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="experiences_attributes", joinColumns=@JoinColumn(name="experience_id"))
    private Map<String, String> attributes = new HashMap<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;
}
