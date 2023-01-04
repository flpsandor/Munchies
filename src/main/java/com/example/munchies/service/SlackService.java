package com.example.munchies.service;

import com.example.munchies.model.entity.GroupOrder;
import com.example.munchies.model.entity.OrderItem;
import com.example.munchies.repository.GroupOrderRepository;
import com.example.munchies.repository.OrderItemRepository;
import com.example.munchies.repository.RestaurantRepository;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SlackService {
    private final RestaurantRepository restaurantRepository;
    private final GroupOrderRepository groupOrderRepository;
    private final OrderItemRepository orderItemRepository;

    public SlackService(RestaurantRepository restaurantRepository, GroupOrderRepository groupOrderRepository, OrderItemRepository orderItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.groupOrderRepository = groupOrderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<LayoutBlock> findAllForSlack() {
        List<LayoutBlock> restaurants = new ArrayList<>();
        for (var restaurant : restaurantRepository.findAll()) {
            restaurants.add(SectionBlock.builder().text(MarkdownTextObject.builder().text("*" + restaurant.getRestaurantName() + "*").build()).build());
            restaurants.add(SectionBlock.builder().fields(Arrays.asList(MarkdownTextObject.builder().text("ADDRESS: " + restaurant.getRestaurantAddress()).build(), MarkdownTextObject.builder().text("PHONE NUMBER: " + restaurant.getRestaurantPhoneNumber()).build(), MarkdownTextObject.builder().text("MENU URL: " + restaurant.getRestaurantMenuUrl()).build(), MarkdownTextObject.builder().text("DELIVERY TIME: " + restaurant.getDeliveryInfo().getDeliveryInfoTime()).build())).build());
        }
        return restaurants;
    }

    public List<LayoutBlock> createGroupOrderFromSlack(String employee, String restaurantShortName, Integer timeout) {
        var restaurant = restaurantRepository.findByRestaurantShortName(restaurantShortName);
        int time;
        if (timeout > 10) {
            time = timeout;
        } else {
            time = 10;
        }
        var groupOrder = new GroupOrder();
        restaurant.ifPresent(value -> {
            groupOrder.setGroupOrderTimeout(time);
            groupOrder.setGroupOrderEmployee(employee);
            groupOrder.setRestaurant(restaurant.get());
            groupOrder.setGroupOrderCreated(LocalDateTime.now());
            groupOrderRepository.save(groupOrder);
        });
        List<LayoutBlock> newGroupOrder = new ArrayList<>();
        newGroupOrder.add(SectionBlock.builder().fields(Arrays.asList(MarkdownTextObject.builder().text("GROUP ORDER ID: " + groupOrder.getGroupOrderId()).build(), MarkdownTextObject.builder().text("ORDER URL: " + "https://37d9-77-105-13-4.eu.ngrok.io/restaurants/order/" + groupOrder.getGroupOrderId() + "/group-order-items").build())).build());
        newGroupOrder.add(SectionBlock.builder().fields(Arrays.asList(MarkdownTextObject.builder().text("INITIALIZED BY: " + groupOrder.getGroupOrderEmployee()).build(), MarkdownTextObject.builder().text("GROUP ORDER TIMEOUT: " + groupOrder.getGroupOrderTimeout()).build(), MarkdownTextObject.builder().text("RESTAURANT NAME: " + groupOrder.getRestaurant().getRestaurantName()).build(), MarkdownTextObject.builder().text("RESTAURANT DELIVERY TIME: " + groupOrder.getRestaurant().getDeliveryInfo().getDeliveryInfoTime()).build(), MarkdownTextObject.builder().text("PHONE NUMBER: " + groupOrder.getRestaurant().getRestaurantPhoneNumber()).build(), MarkdownTextObject.builder().text("MENU URL: " + groupOrder.getRestaurant().getRestaurantMenuUrl()).build())).build());
        return newGroupOrder;
    }

    public List<LayoutBlock> createOrderItemFromSlack(Long orderId, String employee, String itemDescription, Double itemPrice) {
        var orderItem = new OrderItem();
        var groupOrderDb = groupOrderRepository.findById(orderId);
        groupOrderDb.ifPresent(value -> {
            orderItem.setGroupOrder(groupOrderDb.get());
            orderItem.setOrderItemDescription(itemDescription);
            orderItem.setOrderItemEmployee(employee);
            orderItem.setOrderItemPrice(itemPrice);
            orderItemRepository.save(orderItem);
        });
        List<LayoutBlock> item = new ArrayList<>();
        item.add(SectionBlock.builder().text(MarkdownTextObject.builder().text("*ITEM INSERTED IN ORDER*").build()).build());
        item.add(SectionBlock.builder().fields(
                Arrays.asList(
                        MarkdownTextObject.builder().text("EMPLOYEE: "+orderItem.getOrderItemEmployee()).build(),
                        MarkdownTextObject.builder().text("ORDER_ID: "+orderItem.getGroupOrder().getGroupOrderId()).build(),
                        MarkdownTextObject.builder().text("ITEM PRICE: "+orderItem.getOrderItemPrice()).build(),
                        MarkdownTextObject.builder().text("ITEM DESCRIPTION: "+orderItem.getOrderItemDescription()).build()
                )
        ).build());
        return item;
    }

    public List<LayoutBlock> findAllByGroupIdFromSlack(Long orderId) {
        var groupOrder = groupOrderRepository.findById(orderId);
        List<LayoutBlock> orderInfo = new ArrayList<>();
        groupOrder.ifPresent(value -> {
            orderInfo.add(SectionBlock.builder().fields(Arrays.asList(
                    MarkdownTextObject.builder().text("GROUP ORDER ID: " + groupOrder.get().getGroupOrderId()).build(),
                    MarkdownTextObject.builder().text("ORDER URL: " + "https://37d9-77-105-13-4.eu.ngrok.io/restaurants/order/" + groupOrder.get().getGroupOrderId() + "/group-order-items").build(),
                    MarkdownTextObject.builder().text("INITIALIZED BY: " + groupOrder.get().getGroupOrderEmployee()).build(),
                    MarkdownTextObject.builder().text("GROUP ORDER TIMEOUT: " + groupOrder.get().getGroupOrderTimeout()).build(),
                    MarkdownTextObject.builder().text("RESTAURANT NAME: " + groupOrder.get().getRestaurant().getRestaurantName()).build(),
                    MarkdownTextObject.builder().text("RESTAURANT DELIVERY TIME: " + groupOrder.get().getRestaurant().getDeliveryInfo().getDeliveryInfoTime()).build(),
                    MarkdownTextObject.builder().text("PHONE NUMBER: " + groupOrder.get().getRestaurant().getRestaurantPhoneNumber()).build(),
                    MarkdownTextObject.builder().text("MENU URL: " + groupOrder.get().getRestaurant().getRestaurantMenuUrl()).build())).build());
            orderInfo.add(SectionBlock.builder().text(MarkdownTextObject.builder().text("*ORDER ITEMS: *").build()).build());
            for (var item : orderItemRepository.findAllByGroupOrder(groupOrder.get())) {
                orderInfo.add(SectionBlock.builder().fields(Arrays.asList(MarkdownTextObject.builder().text("EMPLOYEE: " + item.getOrderItemEmployee()).build(), MarkdownTextObject.builder().text("PRICE: " + item.getOrderItemPrice()).build())).build());
                orderInfo.add(SectionBlock.builder().text(MarkdownTextObject.builder().text("DECRIPTION: " + item.getOrderItemDescription()).build()).build());
            }
        });
        return orderInfo;
    }
}
