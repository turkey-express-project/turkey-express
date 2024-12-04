package com.currency.turkey_express.global.base.entity;
import com.currency.turkey_express.global.base.enums.store.Category;
import com.currency.turkey_express.global.base.enums.store.StoreStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Time;
import lombok.Getter;

@Entity
@Getter
public class Store extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String storeName;

	@Column(nullable = false)
	private Time openTime;
	@Column(nullable = false)
	private Time closeTime;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StoreStatus storeStatus;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	private BigDecimal orderAmount;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public void setUser(User user){
		this.user = user;
	}

	public Store(){}
	public Store(String storeName, Time openTime, Time closeTime, Category category, BigDecimal orderAmount) {
		this.storeName = storeName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.storeStatus = StoreStatus.OPEN;
		this.category = category;
		this.orderAmount = orderAmount;
	}

}
