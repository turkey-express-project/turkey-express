package com.currency.turkey_express.domain.coupon.repository;

import com.currency.turkey_express.global.base.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {


}
