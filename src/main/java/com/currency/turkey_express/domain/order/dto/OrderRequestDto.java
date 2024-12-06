package com.currency.turkey_express.domain.order.dto;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class OrderRequestDto {

	private String address;

	private String phoneNumber;

	private BigDecimal pointPrice = new BigDecimal(0);

	private Long couponId;
}
