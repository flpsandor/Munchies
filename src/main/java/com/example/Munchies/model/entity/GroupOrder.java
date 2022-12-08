package com.example.Munchies.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
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
    @Column(name="group_order_created")
    private LocalDateTime groupOrderCreated;
    @Column(name="group_order_updated")
    private LocalDateTime groupOrderUpdated;
    @Column(name="group_order_employee_id")
    private String employee;

    @ManyToOne
    @JoinColumn(name = "group_order_restaurant_id", referencedColumnName = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany
    private List<OrderItem> orderItemList;
}
