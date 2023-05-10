package com.lgzarturo.api.personal.api.profile;

import com.lgzarturo.api.personal.api.job.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="profiles_jobs")
@IdClass(ProfileJobKey.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileJob {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Profile profile;
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Job job;
    @Column(length = 200)
    private String resume;
    @Column(length = 8000, columnDefinition = "TEXT")
    private String coverLetter;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ProfileJobStatus profileJobStatus;
}
