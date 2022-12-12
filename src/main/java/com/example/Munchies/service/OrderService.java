package com.example.Munchies.service;

import com.example.Munchies.model.dto.GroupOrderCreationDTO;
import com.example.Munchies.model.dto.GroupOrderDTO;
import com.example.Munchies.model.dto.OrderItemCreationDTO;
import com.example.Munchies.model.dto.OrderItemDTO;
import com.example.Munchies.model.entity.GroupOrder;
import com.example.Munchies.model.entity.OrderItem;
import com.example.Munchies.repository.GroupOrderRepository;
import com.example.Munchies.repository.OrderItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final GroupOrderRepository groupOrderRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderItemRepository orderItemRepository, GroupOrderRepository groupOrderRepository, ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.groupOrderRepository = groupOrderRepository;
        this.modelMapper = modelMapper;
    }

    public List<GroupOrderDTO> findAll() {
        List<GroupOrderDTO> groupOrders = new ArrayList<>();
        for (var order : groupOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "groupOrderId"))) {
            if(order.getGroupOrderCreated().plusMinutes(order.getGroupOrderTimeout()).isBefore(LocalDateTime.now())){
                order.setGroupOrderValid(Boolean.FALSE);
            }
            // prebaciti u sql
            if((LocalDate.now().atStartOfDay()).isBefore(order.getGroupOrderCreated())){
                groupOrders.add(modelMapper.map(order, GroupOrderDTO.class));
            }
        }
        return groupOrders;
    }

    public void deleteGroupOrder(Long id) {
        var groupOrderDb = groupOrderRepository.findById(id);
        groupOrderDb.ifPresent(groupOrderRepository::delete);
    }

    public GroupOrderDTO createGroupOrder(GroupOrderCreationDTO groupOrder) {
        var groupOrderSave = modelMapper.map(groupOrder, GroupOrder.class);
        var timeout = groupOrderSave.getGroupOrderTimeout();
        if(timeout == null || timeout==0)
            groupOrderSave.setGroupOrderTimeout(10);
        groupOrderSave.setGroupOrderCreated(LocalDateTime.now());
        groupOrderRepository.save(groupOrderSave);
        return modelMapper.map(groupOrderSave, GroupOrderDTO.class);
    }

    public OrderItemDTO createOrderItem(Long id, OrderItemCreationDTO orderItem) {
        var groupOrder = groupOrderRepository.findById(id);
        if(groupOrder.isEmpty()){
            return null;
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
        return modelMapper.map(groupOrder.get(), GroupOrderDTO.class);
    }
}
