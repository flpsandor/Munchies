package com.example.Munchies.service;

import com.example.Munchies.model.dto.DeliveryInfoDTO;
import com.example.Munchies.model.dto.RestaurantDTO;
import com.example.Munchies.model.entity.DeliveryInfo;
import com.example.Munchies.model.entity.Restaurant;
import com.example.Munchies.repository.DeliveryInfoRepository;
import com.example.Munchies.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final ModelMapper modelMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, DeliveryInfoRepository deliveryInfoRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.deliveryInfoRepository = deliveryInfoRepository;
        this.modelMapper = modelMapper;
    }

    private RestaurantDTO mapRestaurantToDto(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    private Restaurant mapRestaurantToEntity(RestaurantDTO restaurantDTO) {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }

    private DeliveryInfoDTO mapDeliveryInfoToDto(DeliveryInfo deliveryInfo) {
        return modelMapper.map(deliveryInfo, DeliveryInfoDTO.class);
    }

    private DeliveryInfo mapDeliveryInfoToEntity(DeliveryInfoDTO deliveryInfoDTO) {
        return modelMapper.map(deliveryInfoDTO, DeliveryInfo.class);
    }

    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll().stream().map(this::mapRestaurantToDto).collect(Collectors.toList());
    }

    public void save(RestaurantDTO restaurant, DeliveryInfoDTO deliveryInfo) {
        var restaurantSave = mapRestaurantToEntity(restaurant);
        var deliveryInfoSave = mapDeliveryInfoToEntity(deliveryInfo);
        deliveryInfoSave.setRestaurant(restaurantSave);
        deliveryInfoSave.setDeliveryInfoCreated(LocalDateTime.now());
        restaurantSave.setRestaurantCreated(LocalDateTime.now());
        restaurantSave.setRestaurantShortName(restaurant.getRestaurantName().replaceAll("\\s+", "_").toLowerCase());
        deliveryInfoRepository.save(deliveryInfoSave);
        restaurantRepository.save(restaurantSave);
    }

    public void delete(Long id) {
        var restaurant = restaurantRepository.findByRestaurantId(id);
        var deliveryInfo = deliveryInfoRepository.findDeliveryInfoByRestaurant(restaurant);
        deliveryInfoRepository.delete(deliveryInfo);
        restaurantRepository.delete(restaurant);
    }

    public RestaurantDTO createRestaurantDtoById(Long id) {
        return mapRestaurantToDto(restaurantRepository.findByRestaurantId(id));
    }

    public DeliveryInfoDTO createDeliveryDtoFromRestaurantId(Long id) {
        return mapDeliveryInfoToDto(deliveryInfoRepository.findDeliveryInfoByRestaurant(restaurantRepository.findById(id).get()));
    }

    public void update(Long id, RestaurantDTO restaurant, DeliveryInfoDTO deliveryInfo) {
        var restaurantData = mapRestaurantToEntity(restaurant);
        var deliveryInfoData = mapDeliveryInfoToEntity(deliveryInfo);

        var updatedRestaurant = restaurantRepository.findById(id).get();
        updatedRestaurant.setRestaurantName(restaurantData.getRestaurantName());
        updatedRestaurant.setRestaurantShortName(restaurantData.getRestaurantName().replaceAll("\\s+", "_").toLowerCase());
        updatedRestaurant.setRestaurantUpdated(LocalDateTime.now());
        updatedRestaurant.setRestaurantAddress(restaurantData.getRestaurantAddress());
        updatedRestaurant.setRestaurantMenuUrl(restaurantData.getRestaurantMenuUrl());
        updatedRestaurant.setRestaurantPhoneNumber(restaurantData.getRestaurantPhoneNumber());

        restaurantRepository.save(updatedRestaurant);

        var updatedDeliveryInfo = deliveryInfoRepository.findDeliveryInfoByRestaurant(updatedRestaurant);
        updatedDeliveryInfo.setDeliveryInfoUpdated(LocalDateTime.now());
        updatedDeliveryInfo.setDeliveryInfoTime(deliveryInfoData.getDeliveryInfoTime());
        updatedDeliveryInfo.setDeliveryInfoAdditionalCharges(deliveryInfoData.getDeliveryInfoAdditionalCharges());

        deliveryInfoRepository.save(updatedDeliveryInfo);
    }

}
