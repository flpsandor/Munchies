package com.example.Munchies.repository;

import com.example.Munchies.model.entity.DeliveryInfo;
import com.example.Munchies.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
    Optional<DeliveryInfo> findDeliveryInfoByRestaurant(Restaurant restaurant);
}
