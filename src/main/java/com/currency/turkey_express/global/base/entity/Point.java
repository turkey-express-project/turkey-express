package com.currency.turkey_express.global.base.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "point")
@Getter
@Entity
public class Point extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //포인트 id

	//연관관계 - N:1
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; //사용자 id(외래키)

	@Column(name = "point")
	private Integer point; //적립금액


}
