package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupOrderCreationDTO {
    @NotNull (message = "This field is required")
    private String employee;
    //@NotNull (message = "This field is required")
    private Restaurant restaurant;
}
