package com.example.Munchies.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreationDTO {
        @NotBlank(message = "Field is required")
        @Size(min = 5, message = "Minimal size of restaurant name is five character")
        private String restaurantName;
        @NotBlank(message = "Field is required")
        @Size(min = 5, message = "Address is not in valid format")
        private String restaurantAddress;
        @NotBlank(message = "Field is required")
        @Pattern(regexp = "[0-9]+", message = "Phone number is not in valid format")
        private String restaurantPhoneNumber;
        @NotBlank(message = "Field is required")
        @URL(message = "URL must start with https://")
        private String restaurantMenuUrl;
        @PositiveOrZero(message = "Delivery time must be greater than zero")
        private int deliveryInfoTime;
        @PositiveOrZero(message = "Delivery charges must be greater than zero")
        private double deliveryInfoAdditionalCharges;
}
