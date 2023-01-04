package com.example.munchies.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String orderItemEmployee;
    private String orderItemDescription;
    private Double orderItemPrice;
}
