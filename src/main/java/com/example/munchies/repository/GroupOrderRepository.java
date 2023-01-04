package com.example.munchies.repository;

import com.example.munchies.model.entity.GroupOrder;
import com.example.munchies.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupOrderRepository extends JpaRepository<GroupOrder, Long> {
    List<GroupOrder> findGroupOrderByRestaurant(Restaurant restaurant);
}
