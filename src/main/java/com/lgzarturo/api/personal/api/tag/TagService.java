package com.lgzarturo.api.personal.api.tag;

public interface TagService {
    Tag save(Tag tag);
    Tag findAndSaveDescription(Long id, String description);
    Tag findById(Long id);
}
