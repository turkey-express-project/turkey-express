package com.currency.turkey_express.domain.cart.repository;

import com.currency.turkey_express.global.base.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	
}
