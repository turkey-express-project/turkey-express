package com.currency.turkey_express.domain.store.dto;

import com.currency.turkey_express.domain.menu.dto.MenuInStoreResponseDto;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.enums.store.Category;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import lombok.Getter;

@Getter
public class StoreMenuResponseDto {
	private String storeName;
	private Time openTime;
	private Time closeTime;
	private BigDecimal orderAmount;
	private Category category;
	private Long favoriteCounts;
	private List<MenuInStoreResponseDto> menus;

	public StoreMenuResponseDto(Store store, Long favoriteCounts, List<MenuInStoreResponseDto> menus) {
		this.storeName = store.getStoreName();
		this.openTime = store.getOpenTime();
		this.closeTime = store.getCloseTime();
		this.orderAmount = store.getOrderAmount();
		this.category = store.getCategory();
		this.favoriteCounts = favoriteCounts;
		this.menus = menus;
	}
}
