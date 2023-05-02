package com.lgzarturo.api.personal.api.content.seo;

import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name="content_seo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Seo extends AbstractAuditable<User, UUID> {
    @Column(length = 80)
    private String title;
    @Column(length = 160)
    private String description;
    @Column(length = 60)
    private String keywords;
    @URL
    private String canonical;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="seo_attributes", joinColumns=@JoinColumn(name="seo_id"))
    private Map<String, String> attributes = new HashMap<>();
}
