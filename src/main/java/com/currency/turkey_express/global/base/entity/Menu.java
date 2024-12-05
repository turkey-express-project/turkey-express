package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.memu.MenuStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

@Table(name = "menu")
@Getter
@Entity
@NoArgsConstructor
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false)
	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	@Setter
	private MenuStatus status;         // ENUM: REGISTER, DELETED

	@Column(length = 200)
	private String image;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuTopCategory> topCategoris = new ArrayList<>();


	public Menu(Store store, String name, BigDecimal price, MenuStatus status, String image) {
		this.store = store;
		this.name = name;
		this.price = price;
		this.status = status;
		this.image = image;
	}

	// 메뉴 정보 수정
	public void update(String name, BigDecimal price, MenuStatus status, String image) {
		this.name = name;
		this.price = price;
		this.status = status;
		this.image = image;
	}
}



