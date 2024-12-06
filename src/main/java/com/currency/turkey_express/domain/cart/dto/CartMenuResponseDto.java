package com.currency.turkey_express.domain.cart.dto;

import com.currency.turkey_express.global.base.entity.Menu;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartMenuResponseDto {

	private Menu menu;

	private List<MenuOptionSet> menuOptionSets;

}
