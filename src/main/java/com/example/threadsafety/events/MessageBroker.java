package com.example.threadsafety.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageBroker {
    public static final AtomicInteger eventsCount = new AtomicInteger(0);
    public static final List<String> createdEventsIdList = Collections.synchronizedList(new ArrayList<>());
    public static List<Message> messages = Collections.synchronizedList(new ArrayList<>());
}
