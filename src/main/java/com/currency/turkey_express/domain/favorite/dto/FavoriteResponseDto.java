package com.currency.turkey_express.domain.favorite.dto;

import com.currency.turkey_express.global.base.entity.Favorite;
import lombok.Getter;

@Getter
public class FavoriteResponseDto {
	private String storeName;

	public FavoriteResponseDto(String storeName) {
		this.storeName = storeName;
	}
	public FavoriteResponseDto() {}

	public FavoriteResponseDto(Favorite favorite) {
		this.storeName = favorite.getStore().getStoreName();
	}
}
