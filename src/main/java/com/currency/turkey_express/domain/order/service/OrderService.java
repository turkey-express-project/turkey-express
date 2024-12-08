package com.currency.turkey_express.domain.order.service;

import com.currency.turkey_express.domain.coupon.dto.CouponResponseDto;
import com.currency.turkey_express.domain.coupon.repository.CouponListRepository;
import com.currency.turkey_express.domain.coupon.repository.CouponRepository;
import com.currency.turkey_express.domain.order.dto.CancleRequestDto;
import com.currency.turkey_express.domain.order.dto.OrderCreateDto;
import com.currency.turkey_express.domain.order.exception.NoExistException;
import com.currency.turkey_express.domain.order.repository.OrderMenuOptionRepository;
import com.currency.turkey_express.domain.order.repository.OrderRepository;
import com.currency.turkey_express.domain.point.repository.PointRepository;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.Coupon;
import com.currency.turkey_express.global.base.entity.CouponList;
import com.currency.turkey_express.global.base.entity.Order;
import com.currency.turkey_express.global.base.entity.OrderMenuOption;
import com.currency.turkey_express.global.base.entity.Point;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.coupon.CouponStatus;
import com.currency.turkey_express.global.base.enums.order.OrderStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderService {

	private final StoreRepository storeRepository;

	private final UserRepository userRepository;

	private final CouponRepository couponRepository;

	private final CouponListRepository couponListRepository;

	private final OrderRepository orderRepository;

	private final PointRepository pointRepository;

	private final OrderMenuOptionRepository orderMenuOptionRepository;


	public CouponResponseDto getCoupon(Long couponId, Long userId) {

		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new NoExistException("선택한 쿠폰이 존재하지 않습니다"));

		CouponList couponList = couponListRepository.findByUserIdAndCouponId(userId, couponId)
			.orElseThrow(() -> new NoExistException("선택한 쿠폰을 보유하고 있지 않습니다."));

		if (couponList.getStatus().equals(CouponStatus.EXPIRED)) {
			throw new IllegalArgumentException("만료된 쿠폰입니다.");
		}

		return new CouponResponseDto(coupon);
	}

	public void createOrder(
		OrderCreateDto orderListData
	) {

		// 가게 최소 금액, 오픈 시간 예외 처리
		Store store = storeRepository.findById(orderListData.getStoreId())
			.orElseThrow(() -> new NoExistException("선택한 가게가 존재하지 않습니다"));

		if (store.getOrderAmount().compareTo(orderListData.getTotalPrice()) > 1) {
			throw new IllegalArgumentException("최소주문금액을 채워야 합니다");
		}

		// 영업 시간 확인
		Time curSystemTime = new Time(System.currentTimeMillis());
		if ((curSystemTime.after(store.getCloseTime()) && curSystemTime.before(
			new Time(23, 59, 59)))
			|| (curSystemTime.before(store.getOpenTime()) && curSystemTime.after(
			new Time(0, 0, 0)))
		) {
			throw new IllegalArgumentException("영업 시간이 아닙니다.");
		}

		// 주문 데이터 DB에 저장
		saveOrders(orderListData);

	}

	private void saveOrders(OrderCreateDto orderListData) {
		String orderGroupIdentifier = UUID.randomUUID().toString();

		orderListData.getMenuList()
			.forEach(orderDetail -> {
				Order order = new Order(
					storeRepository.findById(orderListData.getStoreId())
						.orElseThrow(() -> new RuntimeException("존재하지 않는 가게 입니다"))
					,
					userRepository.findById(orderListData.getUserId())
						.orElseThrow(() -> new RuntimeException("존재하지 않는 유저 입니다"))
					,
					orderListData.getAddress(),
					orderListData.getPhoneNumber(),
					orderDetail.getMenuName(),
					orderDetail.getPrice(),
					orderDetail.getPointValue(),
					orderDetail.getCouponDiscountValue(),
					orderGroupIdentifier,
					orderDetail.getSelectedCount(),
					OrderStatus.ORDER_COMPLETE
				);

				Order savedOrder = orderRepository.save(order);

				orderDetail.getOptions().forEach(option -> {
					OrderMenuOption orderMenuOption = new OrderMenuOption(
						savedOrder,
						option.getTopCategoryName(),
						option.getSubCategoryName(),
						option.getOptionPrice()
					);

					orderMenuOptionRepository.save(orderMenuOption);
				});
			});
	}

	public void subtractPoint(Long userId, BigDecimal pointPrice) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new NoExistException("존재하지 않는 유저입니다"));

		Point point = new Point(
			user,
			pointPrice.multiply(BigDecimal.valueOf(-1)));

		pointRepository.save(point);
	}


	public void expireCoupon(Long userId, Long couponId) {
		CouponList coupon = couponListRepository.findByUserIdAndCouponId(userId, couponId)
			.orElseThrow(() -> new NoExistException("존재하지 않는 쿠폰입니다"));

		coupon.setStatus(CouponStatus.EXPIRED);
	}

	public OrderStatus processNext(Long orderId, Long userId) {

		// 요청 검증 단계

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new NoExistException("존재하지 않는 주문입니다"));

		if (!order.getStore().getUser().getId().equals(userId)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
		}

		if (order.getOrderStatus().equals(OrderStatus.ORDER_REJECTED)) {
			throw new IllegalArgumentException("거절된 주문을 진행할 수 없습니다");
		}

		OrderStatus[] orderStatuses = OrderStatus.values();

		int nextOrderIdx = order.getOrderStatus().ordinal() + 1;

		if (orderStatuses.length <= nextOrderIdx) {
			throw new IllegalArgumentException("이미 완료된 주문입니다");
		}

		// 주문 목록 가져오기
		List<Order> orders = orderRepository.findByOrderGroupIdentifier(
			order.getOrderGroupIdentifier());

		// 주문 목록들 상태 업데이트
		orders
			.forEach(o -> {
				o.setOrderStatus(orderStatuses[nextOrderIdx]);
			});

		// 주문 완료 상태 일 때, 포인트 적립

		if (!orderStatuses[nextOrderIdx].equals(OrderStatus.DELIVERY_COMPLETE)) {
			return orderStatuses[nextOrderIdx];
		}

		BigDecimal ordersTotalPrice = orders.stream()
			.map(o -> {
				BigDecimal menuPrice = o.getMenuPrice().subtract(o.getPointPrice())
					.subtract(o.getCouponPrice());

				BigDecimal menuOptionPrice = o.getOrderMenuOptions().stream()
					.map(OrderMenuOption::getOptionPrice)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
				return menuPrice.add(menuOptionPrice);
			})
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal addedPointPrice =
			ordersTotalPrice.divide(BigDecimal.valueOf(100), RoundingMode.DOWN)
				.multiply(BigDecimal.valueOf(3));

		pointRepository.save(new Point(
			orders.get(0).getUser(), addedPointPrice
		));

		return orderStatuses[nextOrderIdx];
	}

	public void cancleOrder(Long orderId, Long userId, CancleRequestDto cancleRequestDto) {

		// 요청 검증 단계

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));

		if (!user.getUserType().equals(UserType.OWNER)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
		}

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new NoExistException("존재하지 않는 주문입니다"));

		if (!order.getStore().getUser().getId().equals(userId)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
		}

		if (order.getOrderStatus().equals(OrderStatus.ORDER_REJECTED)) {
			throw new IllegalArgumentException("이미 거절된 주문입니다");
		}

		if (!order.getOrderStatus().equals(OrderStatus.ORDER_COMPLETE)) {
			throw new IllegalArgumentException("진행되는 주문을 거절할 수 없습니다");
		}

		// 주문 목록 가져오기
		List<Order> orders = orderRepository.findByOrderGroupIdentifier(
			order.getOrderGroupIdentifier());

		// 주문 목록들 상태 업데이트
		orders.forEach(o -> {
			o.setOrderStatus(OrderStatus.ORDER_REJECTED);
			order.setCancleComment(cancleRequestDto.getComment());
		});

		BigDecimal orderPointPrice = orders.stream()
			.map(Order::getPointPrice
			)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		pointRepository.save(new Point(
			orders.get(0).getUser(), orderPointPrice
		));
	}

	public BigDecimal getCustomerPointTotal(Long userId) {
		return pointRepository.getTotalPointsByUserId(userId);
	}
}
