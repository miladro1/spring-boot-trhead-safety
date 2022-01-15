package com.example.threadsafety;

import lombok.SneakyThrows;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;

import java.net.URI;

public class PostRequestTask<T> implements Runnable {
    private final URI url;
    private final HttpEntity<T> request;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    public PostRequestTask(URI url, HttpEntity<T> request) {
        this.url = url;
        this.request = request;
    }

    @SneakyThrows
    @Override
    public void run() {
        this.restTemplate.postForEntity(url, request, String.class);
    }
}
