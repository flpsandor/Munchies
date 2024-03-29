package com.example.munchies.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name="group_order")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GroupOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="group_order_id")
    private Long groupOrderId;
    @Column(name="group_order_timeout")
    private Integer groupOrderTimeout;
    @Column(name="group_order_total_price")
    private Double groupOrderTotalPrice;
    @Column(name="group_order_created")
    private LocalDateTime groupOrderCreated;
    @Column(name="group_order_updated")
    private LocalDateTime groupOrderUpdated;
    @Column(name="group_order_employee_id")
    private String groupOrderEmployee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_order_restaurant_id", referencedColumnName = "restaurant_id")
    private Restaurant restaurant;
}
