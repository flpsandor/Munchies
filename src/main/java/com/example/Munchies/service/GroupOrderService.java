package com.example.Munchies.service;

import com.example.Munchies.repository.GroupOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupOrderService {
    private final GroupOrderRepository groupOrderRepository;

    public GroupOrderService(GroupOrderRepository groupOrderRepository) {
        this.groupOrderRepository = groupOrderRepository;
    }
}
