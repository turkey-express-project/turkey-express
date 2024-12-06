package com.currency.turkey_express.domain.order.dto;

import com.currency.turkey_express.domain.cart.dto.CartCookieDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateDto {

	private BigDecimal totalPrice;

	private Long storeId;

	private Long userId;

	private String address;

	private String phoneNumber;

	private Long couponId;

	private List<OrderDetail> menuList = new ArrayList<>();


	public OrderCreateDto(
		Long userId,
		BigDecimal couponDiscountValue,
		CartCookieDto cartCookieDto,
		OrderRequestDto orderRequestDto
	) {

		this.totalPrice = cartCookieDto.getTotalPrice();
		this.storeId = cartCookieDto.getStoreId();
		this.userId = userId;
		this.address = orderRequestDto.getAddress();
		this.phoneNumber = orderRequestDto.getPhoneNumber();
		this.couponId = orderRequestDto.getCouponId();

		// 장바구니있는 메뉴 데이터를 주문을 위한 데이터로 변환

		menuList = cartCookieDto.getMenuList().stream()
			.map(menu -> {
				return new OrderDetail(
					menu.getMenuName(),
					menu.getSelectedCount(),
					menu.getPrice(),
					BigDecimal.ZERO,
					BigDecimal.ZERO,
					menu.getOptions()
				);
			})
			.toList();

		// 쿠폰 및 포인트 할인 정보는 장바구니에 담긴 메뉴 중 첫번째에 전부 적용
		menuList.get(0).setPointValue(orderRequestDto.getPointPrice());
		menuList.get(0).setPointValue(couponDiscountValue);
	}


}
