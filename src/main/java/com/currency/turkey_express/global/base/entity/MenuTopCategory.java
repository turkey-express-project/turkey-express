package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.memu.NecessaryStatus;
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
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "top_category")
@NoArgsConstructor
public class MenuTopCategory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", nullable = false)
	private Menu menu;

	@Column(nullable = false, length = 20)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected NecessaryStatus necessary;        // ENUM: OPTIONAL, REQUIRED

	@OneToMany(mappedBy = "topCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuSubCategory> subCategories = new ArrayList<>();


	public MenuTopCategory(Menu menu, String title, NecessaryStatus necessary) {
		this.menu = menu;
		this.title = title;
		this.necessary = necessary;
	}

}

