package com.example.Munchies.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private Long restaurantId;
    @NotBlank(message = "Field is required")
    @Size(min = 5, message = "Minimal size of restaurant name is five character")
    private String restaurantName;
    private String restaurantShortName;
    @NotBlank(message = "Field is required")
    @Size(min = 5, message = "Address is not in valid format")
    private String restaurantAddress;
    @NotBlank(message = "Field is required")
    @Pattern(regexp = "[0-9]+", message = "Phone number is not in valid format")
    private String restaurantPhoneNumber;
    @NotBlank(message = "Field is required")
    @URL(message = "URL must start with https://")
    private String restaurantMenuUrl;
    private LocalDateTime restaurantCreated;
    private LocalDateTime restaurantUpdated;
}
