package com.currency.turkey_express.global.base.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "review")
@Getter
@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(name = "point", nullable = false)
	private int point;          // 별점 점수(1 ~ 5 범위)

	@Column(name = "contents", nullable = false, length = 255)
	private String contents;

	@Column(name = "create_at", nullable = false)
	private LocalDateTime createAt;


	public Review(Order order, int point, String contents, LocalDateTime createAt) {
		this.order = order;
		this.point = point;
		this.contents = contents;
		this.createAt = LocalDateTime.now();
	}

	public Review() {
		this.createAt = LocalDateTime.now();
	}


}
