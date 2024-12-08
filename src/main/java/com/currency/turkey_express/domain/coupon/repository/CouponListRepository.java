package com.currency.turkey_express.domain.coupon.repository;

import com.currency.turkey_express.global.base.entity.CouponList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponListRepository extends JpaRepository<CouponList, Long> {

	Optional<CouponList> findByUserIdAndCouponId(Long userId, Long couponId);

	List<CouponList> findAllByUserId(Long userId);


	//쿠폰 만료일이 현재 시간보다 작거나 같고, 상태가 ok인 쿠폰 리스트 조회
	@Query("SELECT cl "
		+ "FROM CouponList cl "
		+ "WHERE cl.coupon.endDate <= CURRENT_TIMESTAMP "
		+ "AND cl.status = 'OK'")
	List<CouponList> findByCouponEndDateAfterAndStatusOk();
}
