package com.example.munchies.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String orderItemEmployee;
    private String orderItemDescription;
    private Double orderItemPrice;
}
