package com.example.threadsafety.events;

import lombok.Data;

@Data
public class Message {
    private final String eventId;
    private final String content;
}
