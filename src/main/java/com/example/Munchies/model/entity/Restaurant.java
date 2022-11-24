package com.example.Munchies.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "restaurant_address")
    private String restaurantAddress;
    @Column(name = "restaurant_phone_number")
    private String restaurantPhoneNumber;
    @Column(name="restaurant_menu_url")
    private String restaurantMenuUrl;
    @Column(name="restaurant_created")
    private LocalDateTime restaurantCreated;
    @Column(name="restaurant_updated")
    private LocalDateTime restaurantUpdated;
}
