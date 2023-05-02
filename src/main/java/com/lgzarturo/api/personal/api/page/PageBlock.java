package com.lgzarturo.api.personal.api.page;

import com.lgzarturo.api.personal.api.content.block.ContentBlock;
import com.lgzarturo.api.personal.api.content.seo.Seo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="pages_blocks")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"page_id", "block_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageBlock extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name = "page_id")
    @NotNull
    private Page page;
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
