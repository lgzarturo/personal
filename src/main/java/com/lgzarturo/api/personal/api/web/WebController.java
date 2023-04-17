package com.lgzarturo.api.personal.api.web;

import com.lgzarturo.api.personal.client.jsonplaceholder.PlaceHolderClient;
import com.lgzarturo.api.personal.client.jsonplaceholder.Post;
import com.lgzarturo.api.personal.config.AppConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/web")
@Slf4j
public class WebController {

    private final AppConfigProperties appConfigProperties;
    private final PlaceHolderClient placeHolderClient;

    public WebController(
        AppConfigProperties appConfigProperties,
        PlaceHolderClient placeHolderClient
    ) {
        this.appConfigProperties = appConfigProperties;
        this.placeHolderClient = placeHolderClient;
    }

    private static int counter = 0;

    record PingResponse(String message, LocalDateTime timestamp) {
        public PingResponse(int counter) {
            this("Pong #" + counter, LocalDateTime.now());
        }
    }

    @GetMapping("/ping")
    public PingResponse ping() {
        log.debug("Ping request received at {}", LocalDateTime.now());
        return new PingResponse(++counter);
    }

    record AppInfoResponse(String name, String version, String description, String url, String email) {
        public AppInfoResponse(AppConfigProperties appConfigProperties) {
            this(appConfigProperties.name(), appConfigProperties.version(), appConfigProperties.description(), appConfigProperties.url(), appConfigProperties.email());
        }
    }

    @GetMapping("/info")
    public AppInfoResponse info() {
        return new AppInfoResponse(appConfigProperties);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(placeHolderClient.getPosts());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") int postId) {
        return ResponseEntity.ok(placeHolderClient.getPostById(postId));
    }
}
