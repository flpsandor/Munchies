package com.example.Munchies.repository;

import com.example.Munchies.model.entity.DeliveryInfo;
import com.example.Munchies.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
    DeliveryInfo findDeliveryInfoByRestaurant(Restaurant restaurant);
}
