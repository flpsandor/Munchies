package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.DeliveryInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantShortName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantMenuUrl;
    private LocalDateTime restaurantCreated;
    private LocalDateTime restaurantUpdated;
    private DeliveryInfo deliveryInfo;
}
