package com.currency.turkey_express.domain.cart.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionDetail {

	private String topCategoryName;

	private String subCategoryName;

	private BigDecimal optionPrice;

}
