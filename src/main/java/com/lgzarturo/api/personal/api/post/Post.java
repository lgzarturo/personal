package com.lgzarturo.api.personal.api.post;

import com.lgzarturo.api.personal.api.comment.Comment;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name="posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post extends AbstractAuditable<User, UUID> {
    @Column(length = 240)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTag> tags = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;
}
