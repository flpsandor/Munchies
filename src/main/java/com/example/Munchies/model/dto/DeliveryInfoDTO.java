package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfoDTO {
    private Long deliveryInfoId;
    @PositiveOrZero(message = "Delivery time must be more or equal then zero")
    private int deliveryInfoTime;
    @PositiveOrZero(message = "Delivery charges must be more or equal then zero")
    private double deliveryInfoAdditionalCharges;
    private LocalDateTime deliveryInfoCreated;
    private LocalDateTime deliveryInfoUpdated;
    private Restaurant restaurant;
}
