package com.lgzarturo.api.personal.api.job.dto;

import com.lgzarturo.api.personal.api.job.JobPublication;
import com.lgzarturo.api.personal.api.job.JobStatus;
import com.lgzarturo.api.personal.api.job.JobType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JobRequest {
    @Size(max = 160)
    private String title;
    @Size(max = 240)
    private String description;
    @URL
    private String url;
    @URL
    private String image;
    @Size(max = 80)
    private String company;
    @Size(max = 80)
    private String location;
    @Min(1_000)
    @Max(1_000_000)
    private BigDecimal salary;
    private JobStatus status;
    private Boolean isActive;
    private Boolean isPromoted;
    private JobType type;
    private LocalDate publishedDate;
    private LocalDate validUntilDate;
    //private Set<ProfileJob> profileJobs;
    private Map<String, String> attributes;

    public JobPublication toEntity() {
        return JobPublication.builder()
                .title(title)
                .description(description)
                .url(url)
                .imageUrl(image)
                .company(company)
                .location(location)
                .salary(salary)
                .status(status)
                .isActive(isActive)
                .isPromoted(isPromoted)
                .type(type)
                .publishedDate(publishedDate)
                .validUntilDate(validUntilDate)
                .attributes(attributes)
                .build();
    }

    public void updateEntity(JobPublication jobPublication, JobRequest request) {
        jobPublication.setTitle(request.getTitle());
        jobPublication.setDescription(request.getDescription());
        jobPublication.setUrl(request.getUrl());
        jobPublication.setImageUrl(request.getImage());
        jobPublication.setCompany(request.getCompany());
        jobPublication.setLocation(request.getLocation());
        jobPublication.setSalary(request.getSalary());
        jobPublication.setStatus(request.getStatus());
        jobPublication.setIsActive(request.getIsActive());
        jobPublication.setIsPromoted(request.getIsPromoted());
        jobPublication.setType(request.getType());
        jobPublication.setPublishedDate(request.getPublishedDate());
        jobPublication.setValidUntilDate(request.getValidUntilDate());
        jobPublication.setAttributes(request.getAttributes());
    }
}
