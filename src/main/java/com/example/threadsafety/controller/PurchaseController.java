package com.example.threadsafety.controller;

import com.example.threadsafety.DTO.PurchaseProductDto;
import com.example.threadsafety.service.PurchaseService;
import com.example.threadsafety.service.PurchaseServiceWithObjectFactory;
import com.example.threadsafety.service.PurchaseServiceWithProxiedPrototype;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseServiceWithProxiedPrototype purchaseServiceWithProxiedPrototype;
    private final PurchaseServiceWithObjectFactory purchaseServiceWithObjectFactory;

    public PurchaseController(PurchaseService purchaseService, PurchaseServiceWithProxiedPrototype purchaseServiceWithProxiedPrototype, PurchaseServiceWithObjectFactory purchaseServiceWithObjectFactory) {
        this.purchaseService = purchaseService;
        this.purchaseServiceWithProxiedPrototype = purchaseServiceWithProxiedPrototype;
        this.purchaseServiceWithObjectFactory = purchaseServiceWithObjectFactory;
    }

    @PostMapping
    public boolean purchase(@RequestBody PurchaseProductDto dto) {
        return purchaseService.purchase(dto);
    }

    @PostMapping("/with-proxy")
    public boolean purchaseWithProxiedPrototype(@RequestBody PurchaseProductDto dto) {
        return purchaseServiceWithProxiedPrototype.purchase(dto);
    }

    @PostMapping("/with-object-factory")
    public boolean purchaseWithObjectFactory(@RequestBody PurchaseProductDto dto) {
        return purchaseServiceWithObjectFactory.purchase(dto);
    }
}
