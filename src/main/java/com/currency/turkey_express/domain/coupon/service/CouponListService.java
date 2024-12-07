package com.currency.turkey_express.domain.coupon.service;

import com.currency.turkey_express.domain.coupon.dto.CouponList.CouponListResponseDto;
import com.currency.turkey_express.domain.coupon.repository.CouponListRepository;
import com.currency.turkey_express.domain.coupon.repository.CouponRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.Coupon;
import com.currency.turkey_express.global.base.entity.CouponList;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.coupon.CouponStatus;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponListService {

	private final CouponRepository couponRepository;
	private final CouponListRepository couponListRepository;
	private final UserRepository userRepository;

	/**
	 * 쿠폰 수령(유저별) API 쿠폰 리스트 테이블에 데이터 저장
	 */
	@Transactional
	public CouponListResponseDto receivedCoupon(Long userId, Long couponId,
		Long loginUserId) {

		//사용자 id 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		//로그인한 사용자랑 ID가 일치하는지 확인
		if (!userId.equals(loginUserId)) {
			throw new BusinessException(ExceptionType.USER_NOT_MATCH);
		}

		//쿠폰이 존재하는지 확인
		Coupon coupon = couponRepository.findByIdOrElseThrow(couponId);

		//이미 수령한 쿠폰인지 쿠폰 리스트 확인
		Optional<CouponList> couponList = couponListRepository.findByUserIdAndCouponId(userId,
			couponId);

		// 이미 해당 쿠폰을 수령한 경우 예외 처리
		//couponList 값이 존재하면
		if (couponList.isPresent()) {
			//값이 존재하는데 상태가 OK가 아닌 경우
			if (couponList.get().getStatus() != CouponStatus.OK) {
				throw new BusinessException(ExceptionType.EXPIRED_COUPON);
			}

			//값이 존재하는 경우 -> 이미 존재하는 쿠폰 예외처리
			throw new BusinessException(ExceptionType.ALREADY_RECEIVED_COUPON);
		}

		//콘솔 로그 확인
		log.info("couponId: {}", couponId);

		// 쿠폰 리스트 생성 & 저장
		CouponList newCouponList = new CouponList(user, coupon, CouponStatus.OK);
		couponList = Optional.of(couponListRepository.save(newCouponList));

		return CouponListResponseDto.toDto(couponList.get());
	}

	/**
	 * 쿠폰 목록(유저별) API 쿠폰 리스트 테이블 목록 조회
	 */
	public List<CouponListResponseDto> findAllCouponList(Long userId,
		Long loginUserId) {
		//사용자 id 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		//로그인한 사용자랑 ID가 일치하는지 확인
		if (!userId.equals(loginUserId)) {
			throw new BusinessException(ExceptionType.USER_NOT_MATCH);
		}

		//특정 유저 쿠폰 리스트 조회
		List<CouponList> couponList = couponListRepository.findAllByUserId(userId);

		List<CouponListResponseDto> couponListResponseDtoList = new ArrayList<>();

		for (CouponList newCouponList : couponList) {
			//CouponList -> CouponListResponseDto 객체로 변환
			CouponListResponseDto couponListResponseDto = CouponListResponseDto.toDto(
				newCouponList);
			couponListResponseDtoList.add(couponListResponseDto);
		}
		return couponListResponseDtoList;
	}

	/**
	 * 쿠폰 만료날짜가 되면 쿠폰 상태 변경 API
	 * - 매일 자정마다 업데이트 실행
	 */
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateCouponStatus() {

		//현재 시간 가져오기
		LocalDateTime now = LocalDateTime.now();

		//만요일이 지나도 상태가 OK인 것을 찾기
		List<Coupon> okCoupons = couponRepository.findByAfterEndDateAndStatusOk(now,
			CouponStatus.OK);

		//
		for (Coupon coupon : okCoupons) {
			coupon.updateCouponStatus(CouponStatus.EXPIRED);
		}
	}

}
