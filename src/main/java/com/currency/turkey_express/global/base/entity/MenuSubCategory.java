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
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "sub_category")
@NoArgsConstructor
public class MenuSubCategory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "top_category_id", nullable = false)
	private MenuTopCategory topCategory;

	@Column(nullable = false, length = 20)
	private String content;

	@Column(nullable = false)
	private BigDecimal extraPrice;


	public MenuSubCategory(MenuTopCategory topCategory, String content, BigDecimal extraPrice) {
		this.topCategory = topCategory;
		this.content = content;
		this.extraPrice = extraPrice;
	}

}
