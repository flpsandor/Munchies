package com.example.munchies.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long orderItemId;
    @Column(name="order_item_description")
    private String orderItemDescription;
    @Column(name="order_item_price")
    private Double orderItemPrice;
    @Column(name="order_item_created")
    private LocalDateTime orderItemCreated;
    @Column(name="order_item_updated")
    private LocalDateTime orderItemUpdated;
    @Column(name="order_item_employee_id")
    private String orderItemEmployee;

    @ManyToOne
    @JoinColumn(name="order_item_group_order_id", referencedColumnName = "group_order_id")
    private GroupOrder groupOrder;
}
