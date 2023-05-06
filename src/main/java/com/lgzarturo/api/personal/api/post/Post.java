package com.lgzarturo.api.personal.api.post;

import com.lgzarturo.api.personal.api.comment.Comment;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name="posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post extends AbstractAuditable<User, Long> {
    @Column(length = 240)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Comment> comments;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTag> tags;
    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    public void addComment(Comment comment) {
        if (comments == null) comments = new HashSet<>();
        comments.add(comment);
        comment.setPost(this);
    }

    public void addTag(PostTag tag) {
        if (tags == null) tags = new HashSet<>();
        tags.add(tag);
        tag.setPost(this);
    }
}
