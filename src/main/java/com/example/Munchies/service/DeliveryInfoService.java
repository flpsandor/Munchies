package com.example.Munchies.service;

import com.example.Munchies.model.entity.DeliveryInfo;
import com.example.Munchies.model.entity.Restaurant;
import com.example.Munchies.repository.DeliveryInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeliveryInfoService {
    private final DeliveryInfoRepository deliveryInfoRepository;

    public DeliveryInfoService(DeliveryInfoRepository deliveryInfoRepository) {
        this.deliveryInfoRepository = deliveryInfoRepository;
    }

    public void save(Restaurant restaurant, DeliveryInfo deliveryInfo) {
        deliveryInfo.setRestaurant(restaurant);
        deliveryInfo.setDeliveryInfoCreated(LocalDateTime.now());
        deliveryInfoRepository.save(deliveryInfo);
    }

    public Optional<DeliveryInfo> findByRestaurant(Restaurant restaurant){
        return deliveryInfoRepository.findDeliveryInfoByRestaurant(restaurant);
    }

    public void update(Restaurant restaurant, DeliveryInfo deliveryInfo){
        Optional<DeliveryInfo> deliveryInfoDb = deliveryInfoRepository.findDeliveryInfoByRestaurant(restaurant);
        if(deliveryInfoDb.isPresent()){
            var updatedDeliveryInfo = deliveryInfoDb.get();
            updatedDeliveryInfo.setRestaurant(restaurant);
            updatedDeliveryInfo.setDeliveryInfoCreated(deliveryInfoDb.get().getDeliveryInfoCreated());
            updatedDeliveryInfo.setDeliveryInfoUpdated(LocalDateTime.now());
            updatedDeliveryInfo.setDeliveryInfoTime(deliveryInfo.getDeliveryInfoTime());
            updatedDeliveryInfo.setDeliveryInfoAdditionalCharges(deliveryInfo.getDeliveryInfoAdditionalCharges());
            deliveryInfoRepository.save(deliveryInfo);
        }
    }

    public void delete(Restaurant restaurant) {
        deliveryInfoRepository.delete(deliveryInfoRepository.findDeliveryInfoByRestaurant(restaurant).get());
    }
}
