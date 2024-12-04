package com.currency.turkey_express.global.base.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "sub_category")
public class MenuSubCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_category_id", nullable = false)
    private MenuTopCategory topCategory;

    @Column(name = "content", nullable = false, length = 20)
    private String content;

    @Column(name = "extra_price", nullable = false)
    private int extraPrice;


    public MenuSubCategory(MenuTopCategory topCategory, String content, int extraPrice) {
        this.topCategory = topCategory;
        this.content = content;
        this.extraPrice = extraPrice;
    }

    public MenuSubCategory() {}

}
