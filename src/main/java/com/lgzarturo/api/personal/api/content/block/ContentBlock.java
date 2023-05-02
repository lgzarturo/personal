package com.lgzarturo.api.personal.api.content.block;

import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity(name="content_blocks")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
@Builder
public class ContentBlock extends AbstractAuditable<User, Long> {
    @Enumerated(EnumType.STRING)
    private ContentBlockType type;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer position;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="content_blocks_items", joinColumns=@JoinColumn(name="content_block_id"))
    private Map<ContentBlock, ContentBlockType> blocks = new HashMap<>();
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="content_block_styles", joinColumns=@JoinColumn(name="content_block_id"))
    private Map<String, String> styles = new HashMap<>();
}
