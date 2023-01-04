package com.example.munchies.model.dto;

import com.example.munchies.model.entity.Restaurant;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupOrderDTO {
    private Long groupOrderId;
    private String groupOrderEmployee;
    private Double groupOrderTotalPrice;
    private Restaurant restaurant;
    private Integer groupOrderTimeout;
    private LocalDateTime groupOrderCreated;
    private Boolean groupOrderValid;

    public String groupOrderUrl;
}
