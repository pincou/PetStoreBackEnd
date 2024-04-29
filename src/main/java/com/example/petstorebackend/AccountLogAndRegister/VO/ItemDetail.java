package com.example.petstorebackend.AccountLogAndRegister.VO;

import lombok.Data;

@Data
public class ItemDetail {
    private String itemId;
    private String category;
    private String product;
    private Double list_price;
    private Double unit_cost;
    private Long quantity;

    private String description;
    private String image;
}
