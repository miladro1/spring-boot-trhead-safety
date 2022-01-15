package com.example.threadsafety.service;

import com.example.threadsafety.DTO.PurchaseProductDto;
import com.example.threadsafety.events.ProductPurchasedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    private ProductPurchasedEvent productPurchasedEvent;

    @Autowired
    public PurchaseService(ProductPurchasedEvent productPurchasedEvent) {
        this.productPurchasedEvent = productPurchasedEvent;
    }

    public boolean purchase(PurchaseProductDto dto) {
        System.out.println("Thread: " + Thread.currentThread().getName() + "product: " + dto.getProduct() + " purchased successfully");
        productPurchasedEvent.setProduct(dto.getProduct());
        productPurchasedEvent.setBuyer(dto.getBuyer());
        return productPurchasedEvent.fire();
    }
}
