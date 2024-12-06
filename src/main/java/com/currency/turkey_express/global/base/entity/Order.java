package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "orders")
@Getter
@Entity
@NoArgsConstructor
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String menuName;

	@Column(nullable = false)
	private BigDecimal menuPrice;

	private BigDecimal pointPrice;
	
	private BigDecimal couponPrice;

	@Column(nullable = false)
	private String orderGroupIdentifier;

	@Column(nullable = false)
	private Integer selectedCount;

	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus;

	@OneToMany(mappedBy = "order")
	private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();


	public Order(Store store, User user, String address, String phoneNumber, String menuName,
		BigDecimal menuPrice, BigDecimal pointPrice, BigDecimal couponPrice,
		String orderGroupIdentifier, Integer selectedCount, OrderStatus orderStatus) {
		this.store = store;
		this.user = user;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.pointPrice = pointPrice;
		this.couponPrice = couponPrice;
		this.orderGroupIdentifier = orderGroupIdentifier;
		this.selectedCount = selectedCount;
		this.orderStatus = orderStatus;
	}
}
