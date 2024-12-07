package com.currency.turkey_express.domain.store.dto;

import com.currency.turkey_express.global.base.enums.store.Category;
import java.math.BigDecimal;
import java.sql.Time;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class StoreRequestDto {

	private String storeName;
	private Time openTime;
	private Time closeTime;
	private BigDecimal orderAmount;
	private Category category;
	private MultipartFile multipartFile;

	public StoreRequestDto(String storeName, Time openTime, Time closeTime, BigDecimal orderAmount,
		Category category, MultipartFile multipartFile) {
		this.storeName = storeName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.orderAmount = orderAmount;
		this.category = category;
		this.multipartFile = multipartFile;
	}
}
