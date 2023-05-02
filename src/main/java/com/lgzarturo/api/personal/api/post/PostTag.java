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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "tag_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostTag extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name = "post_id")
    @NotNull
    private Post post;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotNull
    private Tag tag;
    private Integer position;
}
