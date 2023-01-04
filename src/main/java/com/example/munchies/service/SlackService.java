package com.example.munchies.service;

import com.example.munchies.model.dto.GroupOrderDTO;
import com.example.munchies.model.dto.OrderItemDTO;
import com.example.munchies.model.dto.RestaurantDTO;
import com.example.munchies.model.entity.GroupOrder;
import com.example.munchies.model.entity.OrderItem;
import com.example.munchies.repository.GroupOrderRepository;
import com.example.munchies.repository.OrderItemRepository;
import com.example.munchies.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlackService {
    private final RestaurantRepository restaurantRepository;
    private final GroupOrderRepository groupOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public SlackService(RestaurantRepository restaurantRepository, GroupOrderRepository groupOrderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.groupOrderRepository = groupOrderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    public List<RestaurantDTO> findAllForSlack() {
        List<RestaurantDTO> restaurants = new ArrayList<>();
        for (var restaurant : restaurantRepository.findAll()) {
            restaurants.add(modelMapper.map(restaurant, RestaurantDTO.class));
        }
        return restaurants;
    }

    public GroupOrderDTO createGroupOrderFromSlack(String employee, String restaurantShortName, Integer timeout) {
        var restaurantForSave = restaurantRepository.findByRestaurantShortName(restaurantShortName);
        Integer timeoutForSave = 10;
        if(timeout > 10) {
            timeoutForSave = timeout;
        }
        var groupOrder = new GroupOrder();
        groupOrder.setGroupOrderTimeout(timeoutForSave);
        groupOrder.setGroupOrderEmployee(employee);
        groupOrder.setRestaurant(restaurantForSave.get());
        groupOrder.setGroupOrderCreated(LocalDateTime.now());
        groupOrderRepository.save(groupOrder);
        var tmp = modelMapper.map(groupOrder, GroupOrderDTO.class);
        tmp.setGroupOrderUrl("https://37d9-77-105-13-4.eu.ngrok.io/restaurants/order/"+tmp.getGroupOrderId()+"/group-order-items");
        return tmp;
    }

    public OrderItemDTO createOrderItemFromSlack(Long orderId, String employee, String itemDescription, Double itemPrice) {
        var orderItem  = new OrderItem();
        orderItem.setGroupOrder(groupOrderRepository.findById(orderId).get());
        orderItem.setOrderItemDescription(itemDescription);
        orderItem.setOrderItemEmployee(employee);
        orderItem.setOrderItemPrice(itemPrice);
        orderItemRepository.save(orderItem);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    public GroupOrderDTO findAllByGroupIdFromSlack(Long orderId) {
        var groupOrder = groupOrderRepository.findById(orderId);
        return modelMapper.map(groupOrder.get(), GroupOrderDTO.class);
    }

}
