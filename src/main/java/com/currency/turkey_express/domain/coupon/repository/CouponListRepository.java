package com.currency.turkey_express.domain.coupon.repository;

import com.currency.turkey_express.global.base.entity.CouponList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponListRepository extends JpaRepository<CouponList, Long> {

	Optional<CouponList> findByUserIdAndCouponId(Long userId, Long couponId);

}
