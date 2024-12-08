package com.currency.turkey_express.domain.cart.dto;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuDetail {

	private Integer id;

	private String menuName;

	private Integer selectedCount;

	private BigDecimal price;

	private List<MenuOptionDetail> options = new ArrayList<>();

}