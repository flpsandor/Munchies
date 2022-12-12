package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.GroupOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreationDTO {
    private String orderItemEmployee;
    private String orderItemDescription;
    private Double orderItemPrice;
    private GroupOrder groupOrder;
}
