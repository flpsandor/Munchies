package com.example.Munchies.service;

import com.example.Munchies.repository.DeliveryInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryInfoService {
    private final DeliveryInfoRepository deliveryInfoRepository;

    public DeliveryInfoService(DeliveryInfoRepository deliveryInfoRepository) {
        this.deliveryInfoRepository = deliveryInfoRepository;
    }
}
