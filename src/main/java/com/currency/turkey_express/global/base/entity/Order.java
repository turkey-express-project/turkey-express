package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.order.OrderStatus;
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

@Table(name = "order")
@Getter
@Entity
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

	private String address;

	private String phoneNumber;

	private String menuName;

	private BigDecimal menuPrice;

	private BigDecimal pointPrice;

	private BigDecimal couponPrice;

	private BigDecimal totalOrderPrice;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@OneToMany(mappedBy = "order")
	private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

}
