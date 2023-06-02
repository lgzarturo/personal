package com.lgzarturo.api.personal.api.tag;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class TagServiceJpa implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag findAndSaveDescription(Long id, String description) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setDescription(description);
        return tag;
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow();
    }
}
