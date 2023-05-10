package com.lgzarturo.api.personal.api.page;

import com.lgzarturo.api.personal.api.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="pages_tags")
@IdClass(PageTagKey.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageTag {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Page page;
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Tag tag;
    private Integer position;
}
