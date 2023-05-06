package com.lgzarturo.api.personal.api.comment;

import com.lgzarturo.api.personal.api.post.Post;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Entity(name="comments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Comment extends AbstractAuditable<User, Long> {
    @Column(length = 8000, columnDefinition = "TEXT")
    private String content;
    private Long authorId;
    private Long commentId;
    private Boolean isReply;
    private Boolean isDeleted;
    private Boolean isEdited;
    private Boolean isPinned;
    private Boolean isLiked;
    private Boolean isDisliked;
    private Integer likes;
    private Integer dislikes;
    private Integer replies;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="comment_attributes", joinColumns=@JoinColumn(name="comment_id"))
    private Map<String, String> attributes;
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    private void addAttribute(String name, String value) {
        if (Objects.isNull(attributes)) attributes = new HashMap<>();
        attributes.put(name, value);
    }
}
