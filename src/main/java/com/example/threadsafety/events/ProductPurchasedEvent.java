package com.example.threadsafety.events;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProductPurchasedEvent {
    private String product;
    private String buyer;
    private String eventId;

    ProductPurchasedEvent() {
        this.eventId = RandomStringUtils.randomAlphanumeric(10);
        MessageBroker.eventsCount.incrementAndGet();
        MessageBroker.createdEventsIdList.add(eventId);
    }

    public boolean fire() {
        String message = buyer + " purchased " + product;
        System.out.println("fire method called _ Thread : " + Thread.currentThread().getName() + " " + message + " eventId: " + eventId);
        MessageBroker.messages.add(new Message(eventId, message));
        return true;
    }
}
