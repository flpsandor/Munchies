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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final GroupOrderRepository groupOrderRepository;
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderItemRepository orderItemRepository, GroupOrderRepository groupOrderRepository, ModelMapper modelMapper,
                        RestaurantRepository restaurantRepository) {
        this.orderItemRepository = orderItemRepository;
        this.groupOrderRepository = groupOrderRepository;
        this.modelMapper = modelMapper;
        this.restaurantRepository = restaurantRepository;
    }

    private Boolean isGroupOrderValid(GroupOrder groupOrder){
        return !groupOrder.getGroupOrderCreated().plusMinutes(groupOrder.getGroupOrderTimeout()).isBefore(LocalDateTime.now());
    }

    public List<GroupOrderDTO> findAll() {
        List<GroupOrderDTO> groupOrders = new ArrayList<>();
        for (var order : groupOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "groupOrderId"))) {
            if(isGroupOrderValid(order)){
                var tmp = modelMapper.map(order, GroupOrderDTO.class);
                tmp.setGroupOrderValid(Boolean.TRUE);
                groupOrders.add(tmp);
            }
        }
        return groupOrders;
    }

    public GroupOrderDTO createGroupOrder(GroupOrderCreationDTO groupOrder) {
        var groupOrderSave = modelMapper.map(groupOrder, GroupOrder.class);
        var timeout = groupOrderSave.getGroupOrderTimeout();
        if(timeout == null || timeout==0){
            groupOrderSave.setGroupOrderTimeout(10);
        }
        groupOrderSave.setGroupOrderCreated(LocalDateTime.now());
        groupOrderRepository.save(groupOrderSave);
        var tmp = modelMapper.map(groupOrderSave, GroupOrderDTO.class);
        tmp.setGroupOrderValid(Boolean.TRUE);
        return tmp;
    }

    public OrderItemDTO createOrderItem(Long id, OrderItemCreationDTO orderItem) throws Exception {
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            return null;
        }
        if(!isGroupOrderValid(groupOrder.get())){
            throw new Exception("Group order timeout");
        }
        var orderItemSave = modelMapper.map(orderItem, OrderItem.class);
        orderItemSave.setGroupOrder(groupOrder.get());
        orderItemSave.setOrderItemCreated(LocalDateTime.now());
        orderItemRepository.save(orderItemSave);
        return modelMapper.map(orderItemSave, OrderItemDTO.class);
    }

    public List<OrderItemDTO> findAllByGroupId(Long id) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            return null;
        }
        var totalPrice = groupOrder.get().getRestaurant().getDeliveryInfo().getDeliveryInfoAdditionalCharges();
        for (var order : orderItemRepository.findAllByGroupOrder(groupOrder.get())){
            orderItems.add(modelMapper.map(order, OrderItemDTO.class));
            totalPrice+=order.getOrderItemPrice();
        }
        groupOrder.get().setGroupOrderTotalPrice(totalPrice);
        return orderItems;
    }

    public GroupOrderDTO findById(Long id){
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            return null;
        }
        var tmp = modelMapper.map(groupOrder.get(), GroupOrderDTO.class);
        if (isGroupOrderValid(groupOrder.get())) {
            tmp.setGroupOrderValid(Boolean.TRUE);
        } else {
            tmp.setGroupOrderValid(Boolean.FALSE);
        }
        return tmp;
    }

    public GroupOrderDTO createGroupOrder(Long id, GroupOrderCreationDTO groupOrder) {
        var groupOrderSave = modelMapper.map(groupOrder, GroupOrder.class);
        var restaurant = restaurantRepository.findById(id);
        if(restaurant.isEmpty()){
            return null;
        }
        var timeout = groupOrderSave.getGroupOrderTimeout();
        if(timeout == null || timeout==0) {
            groupOrderSave.setGroupOrderTimeout(10);
        }
        groupOrderSave.setGroupOrderCreated(LocalDateTime.now());
        groupOrderSave.setRestaurant(restaurant.get());
        groupOrderRepository.save(groupOrderSave);
        var tmp = modelMapper.map(groupOrderSave,GroupOrderDTO.class);
        tmp.setGroupOrderValid(Boolean.TRUE);
        return tmp;
    }
}
