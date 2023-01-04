package com.example.munchies.service;

import com.example.munchies.exception.OrderWithRestaurantException;
import com.example.munchies.exception.RestaurantNotExistException;
import com.example.munchies.model.dto.RestaurantCreationDTO;
import com.example.munchies.model.dto.RestaurantDTO;
import com.example.munchies.model.entity.Restaurant;
import com.example.munchies.repository.DeliveryInfoRepository;
import com.example.munchies.repository.GroupOrderRepository;
import com.example.munchies.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final ModelMapper modelMapper;
    private final OrderService orderService;
    private final GroupOrderRepository groupOrderRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, DeliveryInfoRepository deliveryInfoRepository, ModelMapper modelMapper, OrderService orderService, GroupOrderRepository groupOrderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.deliveryInfoRepository = deliveryInfoRepository;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.groupOrderRepository = groupOrderRepository;
    }

    public String setShortName(String name) {
        return name.replaceAll("\\s+", "_").toLowerCase();
    }

    public List<RestaurantDTO> findAll() {
        List<RestaurantDTO> restaurants = new ArrayList<>();
        for (var restaurant : restaurantRepository.findAll()) {
            restaurants.add(modelMapper.map(restaurant, RestaurantDTO.class));
        }
        return restaurants;
    }

    public RestaurantDTO createRestaurant(RestaurantCreationDTO restaurant) {
        var restaurantSave = modelMapper.map(restaurant, Restaurant.class);
        restaurantSave.setRestaurantShortName(setShortName(restaurant.getRestaurantName()));
        restaurantSave.setRestaurantCreated(LocalDateTime.now());
        restaurantSave.setDeliveryInfo();
        restaurantRepository.save(restaurantSave);
        return modelMapper.map(restaurantSave, RestaurantDTO.class);
    }

    @Transactional(rollbackFor = {OrderWithRestaurantException.class})
    public void deleteRestaurant(Long id) throws RestaurantNotExistException, OrderWithRestaurantException {
        var restaurantDb = restaurantRepository.findById(id);
        if (restaurantDb.isEmpty()) {
            throw new RestaurantNotExistException("Restaurant does not exist", new RuntimeException());
        }
        for(var groupOrder : groupOrderRepository.findGroupOrderByRestaurant(restaurantDb.get())){
            if(orderService.isGroupOrderValid(groupOrder)){
                throw new OrderWithRestaurantException("Restaurant cant be deleted have active orders", new RuntimeException());
            }
        }
        deliveryInfoRepository.delete(restaurantDb.get().getDeliveryInfo());
        restaurantRepository.delete(restaurantDb.get());
    }

    public RestaurantDTO restaurantDetails(Long id) throws RestaurantNotExistException {
        var restaurantDb = restaurantRepository.findById(id);
        if (restaurantDb.isEmpty()) {
            throw new RestaurantNotExistException("Restaurant does not exist", new RuntimeException());
        }
        return modelMapper.map(restaurantDb.get(), RestaurantDTO.class);
    }

    public RestaurantCreationDTO restaurantUpdateCreation(Long id) throws RestaurantNotExistException {
        var restaurantDb = restaurantRepository.findById(id);
        if (restaurantDb.isEmpty()) {
            throw new RestaurantNotExistException("Restaurant does not exist", new RuntimeException());
        }
        return modelMapper.map(restaurantDb.get(), RestaurantCreationDTO.class);
    }

    public RestaurantDTO updateRestaurant(Long id, RestaurantCreationDTO restaurant) {
        var restaurantDb = restaurantRepository.findById(id);
        restaurantDb.ifPresent(value -> {
            var restaurantUpdate = restaurantDb.get();
            restaurantUpdate.setRestaurantUpdated(LocalDateTime.now());
            restaurantUpdate.setRestaurantName(restaurant.getRestaurantName());
            restaurantUpdate.setRestaurantAddress(restaurant.getRestaurantAddress());
            restaurantUpdate.setRestaurantShortName(setShortName(restaurant.getRestaurantName()));
            restaurantUpdate.setRestaurantPhoneNumber(restaurant.getRestaurantPhoneNumber());
            restaurantUpdate.getDeliveryInfo().setDeliveryInfoTime(restaurant.getDeliveryInfoTime());
            restaurantUpdate.getDeliveryInfo().setDeliveryInfoAdditionalCharges(restaurant.getDeliveryInfoAdditionalCharges());
            restaurantUpdate.getDeliveryInfo().setDeliveryInfoUpdated(LocalDateTime.now());
            restaurantRepository.save(restaurantUpdate);
        });
        return modelMapper.map(restaurantDb, RestaurantDTO.class);
    }
}
