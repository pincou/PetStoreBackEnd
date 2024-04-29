package com.example.petstorebackend.AccountLogAndRegister.VO;

import lombok.Data;

@Data
public class ItemSummary {
    private String itemId;
    private String category;
    private String product;
    private Double price;
    private Long quantity;
}
