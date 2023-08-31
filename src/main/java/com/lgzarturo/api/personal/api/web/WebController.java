package com.lgzarturo.api.personal.api.web;

import com.lgzarturo.api.personal.api.web.dto.AppInfoResponse;
import com.lgzarturo.api.personal.api.web.dto.PingResponse;
import com.lgzarturo.api.personal.api.web.dto.UniqueIdResponse;
import com.lgzarturo.api.personal.infrastructure.client.jsonplaceholder.JsonPlaceholderClient;
import com.lgzarturo.api.personal.infrastructure.client.jsonplaceholder.Post;
import com.lgzarturo.api.personal.config.AppConfigProperties;
import com.lgzarturo.api.personal.infrastructure.generic.UniqueIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/web")
@Slf4j
public class WebController {

    private final AppConfigProperties appConfigProperties;
    private final JsonPlaceholderClient jsonPlaceholderClient;
    private final UniqueIdGenerator<UUID> uniqueIdGenerator;

    public WebController(
        AppConfigProperties appConfigProperties,
        JsonPlaceholderClient jsonPlaceholderClient,
        UniqueIdGenerator<UUID> uniqueIdGenerator) {
        this.appConfigProperties = appConfigProperties;
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    private static int counter = 0;

    @GetMapping("/ping")
    public PingResponse ping() {
        log.debug("Ping request received at {}", LocalDateTime.now());
        return new PingResponse(++counter);
    }

    @GetMapping("/info")
    public AppInfoResponse info() {
        return new AppInfoResponse(appConfigProperties);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(jsonPlaceholderClient.getPosts());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") int postId) {
        return ResponseEntity.ok(jsonPlaceholderClient.getPostById(postId));
    }

    @GetMapping("/error")
    public ResponseEntity<String> error() {
        throw new RuntimeException("Error to test");
    }

    @GetMapping("/unique-id")
    public ResponseEntity<UniqueIdResponse> uniqueId() {
        return ResponseEntity.ok(new UniqueIdResponse(uniqueIdGenerator.generate()));
    }
}
