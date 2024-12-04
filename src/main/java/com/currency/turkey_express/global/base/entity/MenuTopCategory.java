package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.memu.NecessaryStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "top_category")
public class MenuTopCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "necessary", nullable = false)
    protected NecessaryStatus necessary;        // ENUM: OPTIONAL, REQUIRED

    @OneToMany(mappedBy = "topCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuSubCategory> subCategories = new ArrayList<>();


    public MenuTopCategory(Menu menu, String title, NecessaryStatus necessary) {
        this.menu = menu;
        this.title = title;
        this.necessary = necessary;

    }

    public MenuTopCategory() {
        this.subCategories = new ArrayList<>();
    }

}

