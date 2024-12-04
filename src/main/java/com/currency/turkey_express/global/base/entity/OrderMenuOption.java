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

@Table(name = "order_menu_option")
@Getter
@Entity
@NoArgsConstructor
public class OrderMenuOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(nullable = false)
	private String topCategoryName;

	@Column(nullable = false)
	private String subCategoryName;

	@Column(nullable = false)
	private BigDecimal optionPrice;

	public OrderMenuOption(Order order, String topCategoryName, String subCategoryName,
		BigDecimal optionPrice) {
		this.order = order;
		this.topCategoryName = topCategoryName;
		this.subCategoryName = subCategoryName;
		this.optionPrice = optionPrice;
	}
}
