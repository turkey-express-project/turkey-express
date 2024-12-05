package com.currency.turkey_express.domain.cart.dto;


import com.currency.turkey_express.global.base.entity.Menu;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartCookieDto {

	private BigDecimal totalPrice;

	private Long storeId;

	private List<MenuDetail> menuList = new ArrayList<>();


	public void addMenu(Menu menu, List<MenuOptionSet> menuOptionSets, int count) {

		if (this.storeId == null || !this.storeId.equals(menu.getStore().getId())) {
			emptyCart();
			storeId = menu.getStore().getId();
		}

		List<MenuOptionDetail> menuOptionDetail = menuOptionSets.stream()
			.map(optionSet -> {
				return new MenuOptionDetail(
					optionSet.getTopCategory().getTitle(),
					optionSet.getSubCategory().getContent(),
					optionSet.getSubCategory().getExtraPrice()
				);
			}).toList();

		this.menuList.add(
			new MenuDetail(
				menuList.size(),
				menu.getName(),
				count,
				menu.getPrice(),
				menuOptionDetail
			)
		);

		totalPrice = menu.getPrice()
			.add(
				menuOptionSets.stream()
					.map(set -> set.getSubCategory().getExtraPrice())
					.reduce(BigDecimal.ZERO, BigDecimal::add)
			).multiply(BigDecimal.valueOf(count))
			.add(totalPrice);


	}

	/**
	 * 쇼피 카트 전부 비우기 메서드
	 */
	public void emptyCart() {
		this.totalPrice = BigDecimal.ZERO;
		this.storeId = null;
		this.menuList.clear();
	}
}