package com.currency.turkey_express.domain.coupon.repository;

import com.currency.turkey_express.global.base.entity.Coupon;
import com.currency.turkey_express.global.base.enums.coupon.CouponStatus;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	default Coupon findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(
			() -> new BusinessException(ExceptionType.COUPON_NOT_FOUND)
		);
	}

	List<Coupon> findByAfterEndDateAndStatusOk(LocalDateTime now, CouponStatus couponStatus);
}
