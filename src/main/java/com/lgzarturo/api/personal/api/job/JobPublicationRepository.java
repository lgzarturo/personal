package com.lgzarturo.api.personal.api.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobPublicationRepository extends JpaRepository<JobPublication, Long> {
    List<JobPublication> findAllByIsActiveTrueAndIsPromotedIsTrue();
    @Modifying
    @Query("UPDATE jobs j SET j.isActive = true WHERE j.id = ?1")
    void approveJob(Long id);
    @Modifying
    @Query("UPDATE jobs j SET j.isActive = false WHERE j.id = ?1")
    void closeJob(Long id);
    @Modifying
    @Query("UPDATE jobs j SET j.isPromoted = true WHERE j.id = ?1")
    void promoteJob(Long id);
    @Modifying
    @Query("UPDATE jobs j SET j.isPromoted = false WHERE j.id = ?1")
    void demoteJob(Long id);
    @Modifying
    @Query("UPDATE jobs j SET j.imageUrl = ?2 WHERE j.id = ?1")
    void uploadImage(Long id, String image);
    @Modifying
    @Query("UPDATE jobs j SET j.status = ?2 WHERE j.id = ?1")
    void changeStatus(Long id, JobStatus status);
    @Modifying
    @Query("UPDATE jobs j SET j.publishedDate = ?2 WHERE j.id = ?1")
    void changePublicationDate(Long id, LocalDate publicationDate);
}
