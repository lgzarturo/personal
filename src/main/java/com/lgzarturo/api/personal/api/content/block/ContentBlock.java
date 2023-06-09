package com.lgzarturo.api.personal.api.content.block;

import com.lgzarturo.api.personal.api.page.Page;
import com.lgzarturo.api.personal.api.post.Post;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer position;
    private String description;
    private Boolean visible;
    private Boolean locked;
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
    @ManyToMany
    @JoinTable(
        name = "content_blocks_page",
        joinColumns = @JoinColumn(name = "content_block_id"),
        inverseJoinColumns = @JoinColumn(name = "page_id")
    )
    private Set<Page> pages;
    @ManyToMany
    @JoinTable(
        name = "content_blocks_post",
        joinColumns = @JoinColumn(name = "content_block_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts;
}
