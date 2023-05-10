package com.lgzarturo.api.personal.api.page;

import com.lgzarturo.api.personal.api.tag.Tag;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageTagKey implements Serializable {
    private Page page;
    private Tag tag;
}
