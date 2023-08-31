package com.lgzarturo.api.personal.api.job;

import com.lgzarturo.api.personal.api.job.dto.JobRequest;
import com.lgzarturo.api.personal.api.job.dto.JobResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobPublicationService jobPublicationService;

    public JobController(JobPublicationService jobPublicationService) {
        this.jobPublicationService = jobPublicationService;
    }

    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobPublicationService.getJobs();
    }

    @GetMapping("/page")
    public Page<JobResponse> getJobsByPage(Pageable page) {
        return jobPublicationService.getAll(page);
    }

    @PostMapping("/search")
    public List<JobResponse> search(@RequestBody JobRequest jobRequest) {
        return jobPublicationService.search(jobRequest);
    }

    @GetMapping("/promoted")
    public List<JobResponse> getJobsPromoted() {
        return jobPublicationService.getJobsPromoted();
    }

    @GetMapping("/search")
    public List<JobResponse> search(@RequestParam String title) {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setTitle(title);
        return jobPublicationService.search(jobRequest);
    }

    @PostMapping("/create")
    public JobResponse createJob(@RequestBody  @Valid JobRequest jobRequest) {
        return jobPublicationService.createJob(jobRequest);
    }

    @GetMapping("/{id}")
    public JobResponse getJobById(@PathVariable Long id) {
        return jobPublicationService.getJobById(id);
    }

    @PutMapping("/{id}")
    public JobResponse updateJob(@PathVariable Long id, @RequestBody @Valid JobRequest jobRequest) {
        return jobPublicationService.updateJob(id, jobRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobPublicationService.deleteJob(id);
    }

    @PutMapping("/{id}/approve")
    public void approveJob(@PathVariable Long id) {
        jobPublicationService.approveJob(id);
    }

    @PutMapping("/{id}/close")
    public void closeJob(@PathVariable Long id) {
        jobPublicationService.closeJob(id);
    }

    @PutMapping("/{id}/promote")
    public void promoteJob(@PathVariable Long id) {
        jobPublicationService.promoteJob(id);
    }

    @PutMapping("/{id}/demote")
    public void demoteJob(@PathVariable Long id) {
        jobPublicationService.demoteJob(id);
    }

    @PutMapping("/{id}/upload-image")
    public void uploadImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        String image = jobPublicationService.uploadFile(id, file);
        jobPublicationService.uploadImage(id, image);
    }

    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Long id, @RequestBody JobStatus status) {
        jobPublicationService.changeStatus(id, status);
    }

    @PutMapping("/{id}/change-publication-date")
    public void changePublicationDate(@PathVariable Long id, @RequestBody LocalDate publicationDate) {
        jobPublicationService.changePublicationDate(id, publicationDate);
    }
}
