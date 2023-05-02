package com.lgzarturo.api.personal.api.page;

import com.lgzarturo.api.personal.api.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="pages_tags")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"page_id", "tag_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageTag extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name = "page_id")
    @NotNull
    private Page page;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    @NotNull
    private Tag tag;
    private Integer position;
}
