package com.currency.turkey_express.domain.order.dto;

import lombok.Getter;

@Getter
public class CancleRequestDto {

	private String comment;

	public CancleRequestDto(String comment) {
		this.comment = comment;
	}

}
