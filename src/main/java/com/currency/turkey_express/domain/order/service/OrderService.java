package com.currency.turkey_express.domain.order.service;

import com.currency.turkey_express.domain.coupon.dto.CouponResponseDto;
import com.currency.turkey_express.domain.coupon.repository.CouponListRepository;
import com.currency.turkey_express.domain.coupon.repository.CouponRepository;
import com.currency.turkey_express.domain.order.dto.CancleRequestDto;
import com.currency.turkey_express.domain.order.dto.OrderCreateDto;
import com.currency.turkey_express.domain.order.repository.OrderMenuOptionRepository;
import com.currency.turkey_express.domain.order.repository.OrderRepository;
import com.currency.turkey_express.domain.point.repository.PointRepository;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
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
import java.sql.Time;
import java.util.Arrays;
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


	public CouponResponseDto getCoupon(Long couponId) {
		return new CouponResponseDto(
			couponRepository.findById(couponId)
				.orElseThrow(() -> new RuntimeException("선택한 쿠폰이 존재하지 않습니다"))
		);
	}

	public void createOrder(
		OrderCreateDto orderListData
	) {

		// 가게 최소 금액, 오픈 시간 예외 처리
		Store store = storeRepository.findById(orderListData.getStoreId())
			.orElseThrow(() -> new RuntimeException("선택한 가게가 존재하지 않습니다"));

		if (store.getOrderAmount().compareTo(orderListData.getTotalPrice()) > 1) {
			throw new RuntimeException("최소주문금액을 채워야 합니다");
		}

		// 영업 시간 확인
		Time curSystemTime = new Time(System.currentTimeMillis());
		if ((curSystemTime.after(store.getCloseTime()) && curSystemTime.before(
			new Time(23, 59, 59)))
			|| (curSystemTime.before(store.getOpenTime()) && curSystemTime.after(
			new Time(0, 0, 0)))
		) {
			throw new RuntimeException("영업 시간이 아닙니다.");
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

	public void subtrackPoint(Long userId, BigDecimal pointPrice) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다"));

		Point point = new Point(
			user,
			pointPrice.multiply(BigDecimal.valueOf(-1)));

		pointRepository.save(point);
	}


	public void expireCoupon(Long userId, Long couponId) {
		CouponList coupon = couponListRepository.findByUserIdAndCouponId(userId, couponId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 쿠폰입니다"));

		coupon.setStatus(CouponStatus.EXPIRED);
	}

	public void processNext(Long orderId, Long userId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));

		if (!user.getUserType().equals(UserType.OWNER)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED);
		}

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 주문입니다"));

		if (!order.getStore().getUser().getId().equals(userId)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED);
		}

		if (order.getOrderStatus().equals(OrderStatus.ORDER_REJECTED)) {
			throw new RuntimeException("거절된 주문을 진행할 수 없습니다");
		}

		OrderStatus[] orderStatuses = OrderStatus.values();
		log.info("{}", Arrays.toString(orderStatuses));

		int nextOrderIdx = order.getOrderStatus().ordinal() + 1;
		log.info("현재 인덱스 {}", order.getOrderStatus().ordinal());
		if (orderStatuses.length <= nextOrderIdx) {
			throw new RuntimeException("이미 완료된 주문입니다");
		}

		order.setOrderStatus(orderStatuses[nextOrderIdx]);

	}

	public void cancleOrder(Long orderId, Long userId, CancleRequestDto cancleRequestDto) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));

		if (!user.getUserType().equals(UserType.OWNER)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED);
		}

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 주문입니다"));

		if (!order.getStore().getUser().getId().equals(userId)) {
			throw new BusinessException(ExceptionType.UNAUTHORIZED);
		}

		if (order.getOrderStatus().equals(OrderStatus.ORDER_REJECTED)) {
			throw new RuntimeException("이미 거절된 주문입니다");
		}

		if (!order.getOrderStatus().equals(OrderStatus.ORDER_COMPLETE)) {
			throw new RuntimeException("진행되는 주문을 거절할 수 없습니다");
		}

		order.setCancleComment(cancleRequestDto.getComment());
		order.setOrderStatus(OrderStatus.ORDER_REJECTED);
	}
}
