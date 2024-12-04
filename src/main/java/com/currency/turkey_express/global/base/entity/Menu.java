package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.memu.MenuStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "menu_name", nullable = false, length = 50)
    private String name;

    @Column(name = "menu_price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_status", nullable = false, length = 50)
    private MenuStatus status;         // ENUM: REGISTER, DELETED

    @Column(name = "menu_image", length = 200)
    private String image;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuTopCategory> topCategoris = new ArrayList<>();


    public Menu(String name, int price, MenuStatus status, String image) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.image = image;
    }

    public Menu() {}

}



