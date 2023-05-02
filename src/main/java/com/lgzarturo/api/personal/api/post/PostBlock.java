package com.lgzarturo.api.personal.api.post;

import com.lgzarturo.api.personal.api.content.block.ContentBlock;
import com.lgzarturo.api.personal.api.page.Page;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="posts_blocks")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "block_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostBlock extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name = "post_id")
    @NotNull
    private Post post;
    @ManyToOne
    @JoinColumn(name = "block_id")
    @NotNull
    private ContentBlock block;
    private String name;
    private String description;
    private Integer position;
    private Boolean visible;
    private Boolean locked;
}
