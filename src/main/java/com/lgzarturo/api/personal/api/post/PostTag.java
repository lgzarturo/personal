package com.lgzarturo.api.personal.api.post;

import com.lgzarturo.api.personal.api.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="posts_tags")
@IdClass(PostTagKey.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostTag {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Post post;
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Tag tag;
    private Integer position;
}
