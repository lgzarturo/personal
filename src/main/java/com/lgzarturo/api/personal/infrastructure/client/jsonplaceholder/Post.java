package com.lgzarturo.api.personal.infrastructure.client.jsonplaceholder;

public record Post(
    int userId,
    int id,
    String title,
    String body) {
}
