package com.currency.turkey_express.domain.order.repository;

import com.currency.turkey_express.global.base.entity.OrderMenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuOptionRepository extends JpaRepository<OrderMenuOption, Long> {

}
