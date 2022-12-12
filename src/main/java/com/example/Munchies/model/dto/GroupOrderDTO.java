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
public class GroupOrderDTO {
    private Long groupOrderId;
    private String groupOrderEmployee;
    private Restaurant restaurant;
    private Integer groupOrderTimeout;
    private LocalDateTime groupOrderCreated;
}
