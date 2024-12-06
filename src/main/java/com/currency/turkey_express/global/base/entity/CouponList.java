package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.coupon.CouponStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon_list")
@Getter
@Entity
@NoArgsConstructor
public class CouponList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //쿠폰 리스트 id

	//연관관계 - N:1
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; //사용자 id(외래키)

	//연관관계 - N:1
	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon; //쿠폰 id(외래키)

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CouponStatus status = CouponStatus.OK; //쿠폰 상태 : 디폴트 유효

	//쿠폰 수령
	public CouponList(User user, Coupon coupon, CouponStatus status) {
		this.user = user;
		this.coupon = coupon;
		this.status = status;
	}

	public void setStatus(CouponStatus status) {
		this.status = status;
	}


}
