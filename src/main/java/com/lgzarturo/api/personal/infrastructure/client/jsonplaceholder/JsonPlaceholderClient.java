package com.lgzarturo.api.personal.infrastructure.client.jsonplaceholder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
    value = "jsonplaceholder",
    url = "https://jsonplaceholder.typicode.com"
)
public interface JsonPlaceholderClient {
    @GetMapping("/posts")
    List<Post> getPosts();
    @GetMapping("/posts/{postId}")
    Post getPostById(@PathVariable("postId") int postId);
}
