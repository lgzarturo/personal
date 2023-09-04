package com.lgzarturo.api.personal.api.category;

import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

@EqualsAndHashCode(callSuper = true)
@Entity(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category  extends AbstractAuditable<User, Long> {
    private String name;
    @Column(length = 1_000, columnDefinition = "TEXT")
    private String description;
    private Boolean isActive;
    /*
    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "category", orphanRemoval = true)
    var jobs: List<Job> = arrayListOf()
     */
}
