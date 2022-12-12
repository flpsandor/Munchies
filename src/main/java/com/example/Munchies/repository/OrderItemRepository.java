package com.example.Munchies.repository;

import com.example.Munchies.model.dto.OrderItemDTO;
import com.example.Munchies.model.entity.GroupOrder;
import com.example.Munchies.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByGroupOrder(GroupOrder groupOrder);
}
