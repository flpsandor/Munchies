package com.example.Munchies.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "restaurant_short_name")
    private String restaurantShortName;
    @Column(name = "restaurant_address")
    private String restaurantAddress;
    @Column(name = "restaurant_phone_number")
    private String restaurantPhoneNumber;
    @Column(name = "restaurant_menu_url")
    private String restaurantMenuUrl;
    @Column(name = "restaurant_created")
    private LocalDateTime restaurantCreated;
    @Column(name = "restaurant_updated")
    private LocalDateTime restaurantUpdated;

    public void setDeliveryInfo(){
        deliveryInfo.setRestaurant(this);
        deliveryInfo.setDeliveryInfoCreated(this.restaurantCreated);
        deliveryInfo.setDeliveryInfoUpdated(this.restaurantUpdated);
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "restaurant")
    private DeliveryInfo deliveryInfo;
}
