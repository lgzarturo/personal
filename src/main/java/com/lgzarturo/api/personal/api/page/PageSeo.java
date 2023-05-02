package com.lgzarturo.api.personal.api.page;

import com.lgzarturo.api.personal.api.content.seo.Seo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="pages_seo")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"page_id", "seo_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageSeo extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name = "page_id")
    @NotNull
    private Page page;
    @ManyToOne
    @JoinColumn(name = "seo_id")
    @NotNull
    private Seo seo;
}
