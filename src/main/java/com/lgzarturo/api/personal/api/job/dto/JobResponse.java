package com.lgzarturo.api.personal.api.job.dto;

import com.lgzarturo.api.personal.api.job.JobPublication;

public record JobResponse(Long id, String title, String description, String image, String status, String publicationDate, String createdAt, String untilDate) {
    public JobResponse(JobPublication jobPublication) {
        this(jobPublication.getId(), jobPublication.getTitle(), jobPublication.getDescription(), jobPublication.getImageUrl(), jobPublication.getStatus().name(), jobPublication.getPublishedDate().toString(), jobPublication.getCreatedDate().toString(), jobPublication.getValidUntilDate().toString());
    }
}
