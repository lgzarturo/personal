package com.lgzarturo.api.personal.api.candidate;

import com.lgzarturo.api.personal.api.job.Job;
import com.lgzarturo.api.personal.api.profile.Profile;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name="candidates")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Candidate extends AbstractAuditable<User, UUID> {
    @Column(length = 200)
    private String resume;
    @Column(length = 8000, columnDefinition = "TEXT")
    private String coverLetter;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="candidate_attributes", joinColumns=@JoinColumn(name="candidate_id"))
    private Map<String, String> attributes = new HashMap<>();
    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;
    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;
}
