package com.currency.turkey_express.domain.store.dto;

import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.enums.store.Category;
import com.currency.turkey_express.global.base.enums.store.StoreStatus;
import java.math.BigDecimal;
import java.sql.Time;
import lombok.Getter;

@Getter
public class StoreResponseDto {
	private Long storeId;
	private String storeName;
	private Time openTime;
	private Time closeTime;
	private StoreStatus storeStatus;
	private BigDecimal orderAmount;
	private Category category;

	public StoreResponseDto(
		Long storeId, String storeName, Time openTime, Time closeTime,
		StoreStatus storeStatus, BigDecimal orderAmount, Category category
	) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.storeStatus = storeStatus;
		this.orderAmount = orderAmount;
		this.category = category;
	}

	public StoreResponseDto(Store store){
		this.storeId = store.getId();
		this.storeName = store.getStoreName();
		this.openTime = store.getOpenTime();
		this.closeTime = store.getCloseTime();
		this.storeStatus = store.getStoreStatus();
		this.orderAmount = store.getOrderAmount();
		this.category = store.getCategory();
	}


}
