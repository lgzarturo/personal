package com.lgzarturo.api.personal.api.post;

import com.lgzarturo.api.personal.api.tag.Tag;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostTagKey implements Serializable {
    private Post post;
    private Tag tag;
}
