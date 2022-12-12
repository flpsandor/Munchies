package com.example.Munchies.repository;

import com.example.Munchies.model.dto.OrderItemDTO;
import com.example.Munchies.model.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupOrderRepository extends JpaRepository<GroupOrder, Long> {

}
