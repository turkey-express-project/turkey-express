package com.currency.turkey_express.domain.cart.dto;

import com.currency.turkey_express.global.base.entity.MenuSubCategory;
import com.currency.turkey_express.global.base.entity.MenuTopCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionSet {

	MenuTopCategory topCategory;

	MenuSubCategory subCategory;

}
