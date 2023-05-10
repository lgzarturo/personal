package com.lgzarturo.api.personal.api.profile;

import com.lgzarturo.api.personal.api.job.Job;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileJobKey implements Serializable {
    private Profile profile;
    private Job job;
}
