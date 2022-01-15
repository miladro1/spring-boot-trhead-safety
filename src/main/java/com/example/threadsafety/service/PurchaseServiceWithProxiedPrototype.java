package com.example.threadsafety.service;

import com.example.threadsafety.DTO.PurchaseProductDto;
import com.example.threadsafety.events.ProductPurchasedEvent2;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceWithProxiedPrototype {
    private ProductPurchasedEvent2 productPurchasedEvent;


    public PurchaseServiceWithProxiedPrototype(
            ProductPurchasedEvent2 productPurchasedEvent
    ) {
        this.productPurchasedEvent = productPurchasedEvent;
    }

    public boolean purchase(PurchaseProductDto dto) {
        System.out.println("Thread: " + Thread.currentThread().getName() + "product: " + dto.getProduct() + " purchased successfully");
        productPurchasedEvent.setProduct(dto.getProduct());
        productPurchasedEvent.setBuyer(dto.getBuyer());
        productPurchasedEvent.fire();
        return true;
    }

}
