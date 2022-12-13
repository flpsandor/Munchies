package com.example.Munchies.service;

import com.example.Munchies.model.dto.RestaurantCreationDTO;
import com.example.Munchies.model.dto.RestaurantDTO;
import com.example.Munchies.model.entity.Restaurant;
import com.example.Munchies.repository.DeliveryInfoRepository;
import com.example.Munchies.repository.GroupOrderRepository;
import com.example.Munchies.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final ModelMapper modelMapper;
    private final GroupOrderRepository groupOrderRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, DeliveryInfoRepository deliveryInfoRepository, ModelMapper modelMapper,
                             GroupOrderRepository groupOrderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.deliveryInfoRepository = deliveryInfoRepository;
        this.modelMapper = modelMapper;
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

    public void deleteRestaurant(Long id) {
        var restaurantDb = restaurantRepository.findById(id);
        restaurantDb.ifPresent(value -> {
            deliveryInfoRepository.delete(value.getDeliveryInfo());
            restaurantRepository.delete(value);
        });
    }

    public RestaurantDTO restaurantDetails(Long id) {
        var restaurantDb = restaurantRepository.findById(id);
        if (restaurantDb.isEmpty()) {
            return null;
        }
        return modelMapper.map(restaurantDb.get(), RestaurantDTO.class);
    }

    public RestaurantCreationDTO restaurantUpdateCreation(Long id) {
        var restaurantDb = restaurantRepository.findById(id);
        if (restaurantDb.isEmpty()) {
            return null;
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
