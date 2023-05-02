package com.lgzarturo.api.personal.api.job;

import com.lgzarturo.api.personal.api.candidate.Candidate;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name="jobs")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Job extends AbstractAuditable<User, UUID> {
    @Column(length = 160)
    private String title;
    @Column(length = 240)
    private String description;
    private String url;
    private String image;
    @Column(length = 80)
    private String company;
    @Column(length = 80)
    private String location;
    private BigDecimal salary;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private JobType type;
    private LocalDate publishedDate;
    private LocalDate validUntilDate;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Candidate> candidates = new HashSet<>();
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="job_attributes", joinColumns=@JoinColumn(name="job_id"))
    private Map<String, String> attributes = new HashMap<>();
}