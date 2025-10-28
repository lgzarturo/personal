package com.lgzarturo.api.personal.api.profile;

import com.lgzarturo.api.personal.api.job.JobPublication;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileJobKey implements Serializable {
    private Profile profile;
    private JobPublication jobPublication;
}
