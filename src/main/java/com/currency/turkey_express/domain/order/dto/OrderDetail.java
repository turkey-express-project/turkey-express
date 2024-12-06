package com.currency.turkey_express.domain.order.dto;

import com.currency.turkey_express.domain.cart.dto.MenuOptionDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class OrderDetail {

	private String menuName;

	private Integer selectedCount;

	private BigDecimal price;

	@Setter
	private BigDecimal pointValue;

	@Setter
	private BigDecimal couponDiscountValue;

	private List<MenuOptionDetail> options = new ArrayList<>();


}
