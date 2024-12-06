package com.currency.turkey_express.domain.order.repository;

import com.currency.turkey_express.global.base.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 특정 주문 그룹(orderGroupIdentifier)을 기준으로 주문 목록을 조회
    @Query("SELECT o FROM Order o WHERE o.orderGroupIdentifier = :orderGroupIdentifier")
    List<Order> findByOrderGroupIdentifier(String orderGroupIdentifier);

}
