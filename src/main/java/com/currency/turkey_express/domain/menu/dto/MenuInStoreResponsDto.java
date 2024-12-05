package com.currency.turkey_express.domain.menu.dto;

import com.currency.turkey_express.global.base.entity.Menu;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class MenuInStoreResponsDto {
	private Long menuId;
	private String menuName;
	private BigDecimal menuPrice;

	public MenuInStoreResponsDto() {}
	public MenuInStoreResponsDto(Long menuId, String menuName, BigDecimal menuPrice) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
	}
	public MenuInStoreResponsDto(Menu menu) {
		this.menuId = menu.getId();
		this.menuName = menu.getName();
		this.menuPrice = menu.getPrice();
	}
}
