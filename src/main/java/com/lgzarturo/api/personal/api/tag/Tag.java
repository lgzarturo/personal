package com.lgzarturo.api.personal.api.tag;

import com.lgzarturo.api.personal.api.page.PageTag;
import com.lgzarturo.api.personal.api.post.PostTag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name="tags")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tag extends AbstractPersistable<Long> {
    @Column(length = 80, unique = true)
    private String name;
    @Column(length = 160)
    private String description;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
    private Set<PageTag> pages = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
    private Set<PostTag> posts = new HashSet<>();
}
