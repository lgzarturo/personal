package com.lgzarturo.api.personal.api.page;

import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name="pages")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Page extends AbstractAuditable<User, Long> {
    private UUID uuid = UUID.randomUUID();
    @Column(length = 240)
    private String title;
    @Column(unique = true)
    private String slug;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String featuredImage;
    private Boolean isPublished;
    private Boolean isDeleted;
    private Boolean onMenu;
    private Boolean onFooter;
    private Boolean onSidebar;
    private Integer position;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PageTag> tags = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;
}
