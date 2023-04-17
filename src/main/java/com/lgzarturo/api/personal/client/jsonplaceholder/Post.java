package com.lgzarturo.api.personal.client.jsonplaceholder;

public record Post(
    int userId,
    int id,
    String title,
    String body) {
}
