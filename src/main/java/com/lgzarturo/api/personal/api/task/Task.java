package com.lgzarturo.api.personal.api.task;

import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.time.LocalDateTime;

@Entity(name="tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Task extends AbstractAuditable<User, Long> {
    @Column(length = 180)
    private String title;
    @Column(length = 4000, columnDefinition = "TEXT")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime estimatedAt;
    private Boolean finished;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
