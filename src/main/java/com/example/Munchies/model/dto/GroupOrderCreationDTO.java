package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupOrderCreationDTO {
    @NotNull (message = "This field is required")
    @Size(min = 3, message = "Minimal size of employee name is three character")
    private String groupOrderEmployee;
    @NotNull (message = "This field is required")
    private Restaurant restaurant;
    private Integer groupOrderTimeout;
}
