package com.lgzarturo.api.personal.api.post;

import com.lgzarturo.api.personal.api.content.seo.Seo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="posts_seo")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "seo_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostSeo extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name = "post_id")
    @NotNull
    private Post post;
    @ManyToOne
    @JoinColumn(name = "seo_id")
    @NotNull
    private Seo seo;
}
