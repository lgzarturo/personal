package com.lgzarturo.api.personal.api.content.seo;

import com.lgzarturo.api.personal.api.page.Page;
import com.lgzarturo.api.personal.api.post.Post;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name="content_seo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Seo extends AbstractAuditable<User, Long> {
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
    @CollectionTable(name="content_seo_attributes", joinColumns=@JoinColumn(name="seo_id"))
    private Map<String, String> attributes = new HashMap<>();
    @ManyToMany
    @JoinTable(
        name = "content_seo_pages",
        joinColumns = @JoinColumn(name = "seo_id"),
        inverseJoinColumns = @JoinColumn(name = "page_id")
    )
    private Set<Page> pages;
    @ManyToMany
    @JoinTable(
        name = "content_seo_posts",
        joinColumns = @JoinColumn(name = "seo_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts;
}
