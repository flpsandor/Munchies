package com.example.munchies.repository;

import com.example.munchies.model.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupOrderRepository extends JpaRepository<GroupOrder, Long> {

}
