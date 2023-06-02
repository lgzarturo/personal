package com.lgzarturo.api.personal.api.tag;

import com.lgzarturo.api.personal.api.tag.dto.TagRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tags")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> post(@RequestBody TagRequest request) {
        Tag tag = Tag.builder().name(request.name()).description(request.description()).build();
        return ResponseEntity.ok(tagService.save(tag));
    }

    @PutMapping("/{id}/save-description")
    public ResponseEntity<Tag> put(@PathVariable Long id, @RequestParam String description) {
        return ResponseEntity.ok(tagService.findAndSaveDescription(id, description));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> get(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }
}
