package com.currency.turkey_express.domain.cart.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CartRequestDto {

	private Long menuId;

	private Integer menuCount;

	private List<MenuOption> menuOptions = new ArrayList<MenuOption>();
	
}
