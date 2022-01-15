package com.example.threadsafety.DTO;

import lombok.Data;

@Data
public class PurchaseProductDto {
    private final String product;
    private final String buyer;
}
