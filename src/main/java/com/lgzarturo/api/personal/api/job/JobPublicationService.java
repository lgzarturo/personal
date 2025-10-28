package com.lgzarturo.api.personal.api.job;

import com.lgzarturo.api.personal.api.job.dto.JobRequest;
import com.lgzarturo.api.personal.api.job.dto.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface JobPublicationService {
    Page<JobResponse> getAll(Pageable pageable);
    List<JobResponse> search(JobRequest job);
    List<JobResponse> getJobs();
    List<JobResponse> getJobsPromoted();
    JobResponse getJobById(Long id);
    JobResponse createJob(JobRequest job);
    JobResponse updateJob(Long id, JobRequest job);
    void deleteJob(Long id);
    void approveJob(Long id);
    void closeJob(Long id);
    void promoteJob(Long id);
    void demoteJob(Long id);
    String uploadFile(Long id, MultipartFile file);
    void uploadImage(Long id, String image);
    void changeStatus(Long id, JobStatus status);
    void changePublicationDate(Long id, LocalDate publicationDate);
}
