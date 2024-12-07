package com.currency.turkey_express.global.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "coupon")
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //쿠폰 id

	@Column(nullable = false)
	private String couponName; //쿠폰 이름

	@Column(nullable = false)
	private Integer discountValue; //쿠폰 할인률

	@Column(nullable = false)
	private BigDecimal maxDiscount; //쿠폰 최대 할인금액

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;//쿠폰 생성일자

	@Column(updatable = false)
	private LocalDateTime endDate; //쿠폰 만료일자

	//쿠폰 등록
	public Coupon(String couponName, Integer discountValue, BigDecimal maxDiscount,
		LocalDateTime endDate) {
		this.couponName = couponName;
		this.discountValue = discountValue;
		this.maxDiscount = maxDiscount;
		this.endDate = endDate;
	}

}
