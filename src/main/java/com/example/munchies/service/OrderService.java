package com.example.munchies.service;

import com.example.munchies.model.dto.GroupOrderCreationDTO;
import com.example.munchies.model.dto.GroupOrderDTO;
import com.example.munchies.model.dto.OrderItemCreationDTO;
import com.example.munchies.model.dto.OrderItemDTO;
import com.example.munchies.model.entity.GroupOrder;
import com.example.munchies.model.entity.OrderItem;
import com.example.munchies.repository.GroupOrderRepository;
import com.example.munchies.repository.OrderItemRepository;
import com.example.munchies.repository.RestaurantRepository;
import com.example.munchies.exception.GroupOrderNotExistException;
import com.example.munchies.exception.OrderNotValidException;
import com.example.munchies.exception.RestaurantNotExistException;
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

    private boolean isGroupOrderValid(GroupOrder groupOrder){
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

    public OrderItemDTO createOrderItem(Long id, OrderItemCreationDTO orderItem) throws GroupOrderNotExistException, OrderNotValidException {
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            throw new GroupOrderNotExistException("Group order not exist", new RuntimeException());
        }
        if(!isGroupOrderValid(groupOrder.get())){
            throw new OrderNotValidException("Order is not valid anymore", new RuntimeException());
        }
        var orderItemSave = modelMapper.map(orderItem, OrderItem.class);
        orderItemSave.setGroupOrder(groupOrder.get());
        orderItemSave.setOrderItemCreated(LocalDateTime.now());
        orderItemRepository.save(orderItemSave);
        return modelMapper.map(orderItemSave, OrderItemDTO.class);
    }

    public List<OrderItemDTO> findAllByGroupId(Long id) throws GroupOrderNotExistException {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            throw  new GroupOrderNotExistException("Group order not exist", new RuntimeException());
        }
        var totalPrice = groupOrder.get().getRestaurant().getDeliveryInfo().getDeliveryInfoAdditionalCharges();
        for (var order : orderItemRepository.findAllByGroupOrder(groupOrder.get())){
            orderItems.add(modelMapper.map(order, OrderItemDTO.class));
            totalPrice+=order.getOrderItemPrice();
        }
        groupOrder.get().setGroupOrderTotalPrice(totalPrice);
        return orderItems;
    }

    public GroupOrderDTO findById(Long id) throws GroupOrderNotExistException {
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            throw new GroupOrderNotExistException("Group order not exist", new RuntimeException());
        }
        var tmp = modelMapper.map(groupOrder.get(), GroupOrderDTO.class);
        if (isGroupOrderValid(groupOrder.get())) {
            tmp.setGroupOrderValid(Boolean.TRUE);
        } else {
            tmp.setGroupOrderValid(Boolean.FALSE);
        }
        return tmp;
    }

    public GroupOrderDTO createGroupOrder(Long id, GroupOrderCreationDTO groupOrder) throws RestaurantNotExistException {
        var groupOrderSave = modelMapper.map(groupOrder, GroupOrder.class);
        var restaurant = restaurantRepository.findById(id);
        if(restaurant.isEmpty()){
            throw new RestaurantNotExistException("Restaurant does not exist", new RuntimeException());
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
