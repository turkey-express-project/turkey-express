package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.domain.store.dto.StoreRequestDto;
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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.sql.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Table(name = "store")
@Entity
@Getter
@NoArgsConstructor
public class Store extends BaseEntity {

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

	@Column(nullable = false)
	private BigDecimal orderAmount;

	private String imageKeyValue;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;


	public Store(String storeName, Time openTime, Time closeTime,
		Category category, BigDecimal orderAmount) {
		this.storeName = storeName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.storeStatus = StoreStatus.OPEN;
		this.category = category;
		this.orderAmount = orderAmount;
	}

	public void setStore(StoreRequestDto dto){
		this.storeName = dto.getStoreName();
		this.openTime = dto.getOpenTime();
		this.closeTime = dto.getCloseTime();
		this.storeStatus = StoreStatus.OPEN;
		this.category = dto.getCategory();
		this.orderAmount = dto.getOrderAmount();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setStoreStatusClose() {
		this.storeStatus = StoreStatus.CLOSE;
	}

	public void setImageKeyValue(String imageKeyValue){
		this.imageKeyValue = imageKeyValue;
	}
}
