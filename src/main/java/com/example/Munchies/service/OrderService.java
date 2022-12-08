package com.example.Munchies.service;

import com.example.Munchies.model.dto.GroupOrderCreationDTO;
import com.example.Munchies.model.dto.GroupOrderDTO;
import com.example.Munchies.model.dto.OrderItemCreationDTO;
import com.example.Munchies.model.dto.OrderItemDTO;
import com.example.Munchies.model.entity.GroupOrder;
import com.example.Munchies.model.entity.OrderItem;
import com.example.Munchies.repository.GroupOrderRepository;
import com.example.Munchies.repository.OrderItemRepository;
import com.example.Munchies.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final GroupOrderRepository groupOrderRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderItemRepository orderItemRepository, GroupOrderRepository groupOrderRepository, RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.groupOrderRepository = groupOrderRepository;
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    public List<GroupOrderDTO> findAll() {
        List<GroupOrderDTO> groupOrders = new ArrayList<>();
        for (var order : groupOrderRepository.findAll()) {
            groupOrders.add(modelMapper.map(order, GroupOrderDTO.class));
        }
        return groupOrders;
    }

    public void deleteGroupOrder(Long id) {
        var groupOrderDb = groupOrderRepository.findById(id);
        groupOrderDb.ifPresent(groupOrderRepository::delete);
    }

    public GroupOrderDTO createGroupOrder(GroupOrderCreationDTO groupOrder) {
        var groupOrderSave = modelMapper.map(groupOrder, GroupOrder.class);
        groupOrderSave.setGroupOrderCreated(LocalDateTime.now());
        groupOrderRepository.save(groupOrderSave);
        return modelMapper.map(groupOrderSave, GroupOrderDTO.class);
    }

    public OrderItemDTO createOrderItem(Long id, OrderItemCreationDTO orderItem) {
        var orderItemSave = modelMapper.map(orderItem, OrderItem.class);

        return modelMapper.map(orderItemSave, OrderItemDTO.class);
    }
}
