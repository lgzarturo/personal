package com.lgzarturo.api.personal.api.job;

import com.lgzarturo.api.personal.api.job.dto.JobRequest;
import com.lgzarturo.api.personal.api.job.dto.JobResponse;
import com.lgzarturo.api.personal.config.AppConfigProperties;
import com.lgzarturo.api.personal.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class JobPublicationServiceJpa implements JobPublicationService {

    private final JobPublicationRepository jobPublicationRepository;
    private final AppConfigProperties appConfigProperties;

    public JobPublicationServiceJpa(JobPublicationRepository jobPublicationRepository, AppConfigProperties appConfigProperties) {
        this.jobPublicationRepository = jobPublicationRepository;
        this.appConfigProperties = appConfigProperties;
    }


    @Override
    public Page<JobResponse> getAll(Pageable pageable) {
        log.debug("Listing all jobs");
        return jobPublicationRepository.findAll(pageable).map(JobResponse::new);
    }

    @Override
    public List<JobResponse> search(JobRequest job) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<JobPublication> search = Example.of(job.toEntity(), matcher);
        return jobPublicationRepository.findAll(search).stream().map(JobResponse::new).toList();
    }

    @Override
    public List<JobResponse> getJobs() {
        return jobPublicationRepository.findAll().stream().map(JobResponse::new).toList();
    }

    @Override
    public List<JobResponse> getJobsPromoted() {
        log.debug("Listing all promoted jobs");
        return jobPublicationRepository.findAllByIsActiveTrueAndIsPromotedIsTrue().stream().map(JobResponse::new).toList();
    }

    @Override
    public JobResponse getJobById(Long id) {
        return jobPublicationRepository.findById(id).map(JobResponse::new).orElseThrow();
    }

    @Override
    public JobResponse createJob(JobRequest job) {
        JobPublication jobPublicationEntity = job.toEntity();
        return new JobResponse(jobPublicationRepository.save(jobPublicationEntity));
    }

    @Override
    public JobResponse updateJob(Long id, JobRequest job) {
        JobPublication jobPublicationEntity = jobPublicationRepository.findById(id).orElseThrow();
        job.updateEntity(jobPublicationEntity, job);
        return new JobResponse(jobPublicationRepository.save(jobPublicationEntity));
    }

    @Override
    public void deleteJob(Long id) {
        jobPublicationRepository.deleteById(id);
    }

    @Override
    public void approveJob(Long id) {
        jobPublicationRepository.approveJob(id);
    }

    @Override
    public void closeJob(Long id) {
        jobPublicationRepository.closeJob(id);
    }

    @Override
    public void promoteJob(Long id) {
        jobPublicationRepository.promoteJob(id);
    }

    @Override
    public void demoteJob(Long id) {
        jobPublicationRepository.demoteJob(id);
    }

    @Override
    public String uploadFile(Long id, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        return appConfigProperties.upload().uri() + FileUtils.uploadFile(file, appConfigProperties.upload().dir());
    }

    @Override
    public void uploadImage(Long id, String image) {
        jobPublicationRepository.uploadImage(id, image);
    }

    @Override
    public void changeStatus(Long id,  JobStatus jobStatus) {
        jobPublicationRepository.changeStatus(id, jobStatus);
        if (jobStatus.equals(JobStatus.DELETED)) {
            jobPublicationRepository.closeJob(id);
        }
    }

    @Override
    public void changePublicationDate(Long id, LocalDate publicationDate) {
        jobPublicationRepository.changePublicationDate(id, publicationDate);
    }
}
