package com.example.Munchies.model.dto;

import com.example.Munchies.model.entity.GroupOrder;
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
public class OrderItemCreationDTO {
    @NotNull(message = "This field is required")
    @Size(min = 3, message = "Minimal size of employee name is three character")
    private String orderItemEmployee;
    @Size(min=5, max=1000, message = "Minimal character number is five, maximum is thousand")
    private String orderItemDescription;
    @NotNull(message="This field is required")
    private Double orderItemPrice;
    private GroupOrder groupOrder;
}
