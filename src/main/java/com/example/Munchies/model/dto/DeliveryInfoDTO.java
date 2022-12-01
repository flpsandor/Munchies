package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfoDTO {
    private Long deliveryInfoId;
    private int deliveryInfoTime;
    private double deliveryInfoAdditionalCharges;
    private LocalDateTime deliveryInfoCreated;
    private LocalDateTime deliveryInfoUpdated;
    private Restaurant restaurant;
}
