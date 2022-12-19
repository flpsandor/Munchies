package com.example.munchies.repository;

import com.example.munchies.model.entity.GroupOrder;
import com.example.munchies.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByGroupOrder(GroupOrder groupOrder);
}
