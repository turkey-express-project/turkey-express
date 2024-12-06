package com.currency.turkey_express.global.base.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "point")
@Getter
@Entity
@NoArgsConstructor
public class Point extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //포인트 id

	//연관관계 - N:1
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; //사용자 id(외래키)

	@Column(nullable = false)
	private BigDecimal point; //적립금액

	public Point(User user, BigDecimal point) {
		this.user = user;
		this.point = point;
	}
}
