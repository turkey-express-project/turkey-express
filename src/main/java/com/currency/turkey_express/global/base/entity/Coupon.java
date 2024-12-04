package com.currency.turkey_express.global.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "coupon")
@Getter
@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //쿠폰 id

	@Column(name = "coupon_name")
	private String couponName; //쿠폰 이름

	@Column(name = "discount_value")
	private Integer discountValue; //쿠폰 할인률

	@Column(name = "max_discount")
	private Integer maxDiscount; //쿠폰 최대 할인금액


}
