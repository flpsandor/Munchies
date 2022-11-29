package com.example.Munchies.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="delivery_info")
public class DeliveryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_info_id")
    private Long deliveryInfoId;
    @Column(name="delivery_info_time")
    private int deliveryInfoTime;
    @Column(name="delivery_info_additional_charges")
    private double deliveryInfoAdditionalCharges;
    @Column(name="delivery_info_created")
    private LocalDateTime deliveryInfoCreated;
    @Column(name="delivery_info_updated")
    private LocalDateTime deliveryInfoUpdated;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_info_restaurant_id", referencedColumnName = "restaurant_id")
    private Restaurant restaurant;
}
