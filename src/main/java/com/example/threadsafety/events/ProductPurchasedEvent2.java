package com.example.threadsafety.events;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductPurchasedEvent2 {
    private String product;
    private String buyer;
    private String eventId;

    ProductPurchasedEvent2() {
        this.eventId = RandomStringUtils.randomAlphanumeric(10);
        MessageBroker.eventsCount.incrementAndGet();
        MessageBroker.createdEventsIdList.add(eventId);
    }

    public boolean fire() {
        String message = buyer + " purchased " + product;
        System.out.println("Thread fire: " + Thread.currentThread().getName() + " " + message + " eventId: " + eventId);
        MessageBroker.messages.add(new Message(eventId, message));
        return true;
    }
}
