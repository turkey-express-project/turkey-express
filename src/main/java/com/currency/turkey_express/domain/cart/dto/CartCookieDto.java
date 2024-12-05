package com.currency.turkey_express.domain.cart.dto;


import com.currency.turkey_express.global.base.entity.Menu;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartCookieDto {

	private BigDecimal totalPrice;

	private Long storeId;

	private List<MenuDetail> menuList = new ArrayList<>();

	@Getter
	@AllArgsConstructor
	static class MenuDetail {

		private Integer id;

		private String menuName;

		private Integer selectedCount;

		private BigDecimal price;

		private List<MenuOption> options = new ArrayList<>();

	}

	@Getter
	@AllArgsConstructor
	static class MenuOption {

		private String topCategoryName;

		private String subCategoryName;

		private BigDecimal optionPrice;

	}


	public void addMenu(Menu menu, List<MenuOptionSet> menuOptionSets, int count) {

		if (this.storeId == null || !this.storeId.equals(menu.getStore().getId())) {
			emptyCart();
			storeId = menu.getStore().getId();
		}

		List<MenuOption> menuOption = menuOptionSets.stream()
			.map(optionSet -> {
				return new MenuOption(
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
				menuOption
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