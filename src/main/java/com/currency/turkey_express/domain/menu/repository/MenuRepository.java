package com.currency.turkey_express.domain.menu.repository;

import com.currency.turkey_express.domain.menu.dto.MenuInStoreResponseDto;
import com.currency.turkey_express.global.base.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Query(
		"SELECT new com.currency.turkey_express.domain.menu.dto.MenuInStoreResponseDto(m) "+
			"FROM Menu AS m WHERE m.store.id = :storeId"
	)
	List<MenuInStoreResponseDto> findAllByStoreId(Long storeId);
}
