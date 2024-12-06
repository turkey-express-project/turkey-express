package com.currency.turkey_express.domain.cart.service;

import com.currency.turkey_express.domain.cart.dto.CartMenuResponseDto;
import com.currency.turkey_express.domain.cart.dto.CartRequestDto;
import com.currency.turkey_express.domain.cart.dto.MenuOptionSet;
import com.currency.turkey_express.domain.cart.repository.MenuSubCategoryRepository;
import com.currency.turkey_express.domain.cart.repository.MenuTopCategoryRepository;
import com.currency.turkey_express.domain.menu.repository.MenuRepository;
import com.currency.turkey_express.global.base.entity.Menu;
import com.currency.turkey_express.global.base.enums.store.StoreStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

	private final MenuRepository menuRepository;

	private final MenuTopCategoryRepository menuTopCategoryRepository;

	private final MenuSubCategoryRepository menuSubCategoryRepository;


	@Transactional(readOnly = true)
	public CartMenuResponseDto getSelectedMenuInfo(
		CartRequestDto cartRequestDto
	) {
		Menu menu = menuRepository.findById(cartRequestDto.getMenuId())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 메뉴 입니다."));

		if (menu.getStore().getStoreStatus().equals(StoreStatus.CLOSE)) {
			throw new RuntimeException("폐점한 가게 메뉴입니다");
		}

		List<MenuOptionSet> menuOptionSets =
			cartRequestDto.getMenuOptions().stream()
				.map(optionSet -> {
					return new MenuOptionSet(
						menuTopCategoryRepository.findById(optionSet.getTopCategoryId())
							.orElseThrow(() -> new RuntimeException("존재하지 않는 상위 옵션입니다."))
						,
						menuSubCategoryRepository.findById(optionSet.getSubCategoryId())
							.orElseThrow(() -> new RuntimeException("존재하지 않는 하위 옵션입니다."))
					);
				})
				.toList();

		return new CartMenuResponseDto(menu, menuOptionSets);
	}
}
