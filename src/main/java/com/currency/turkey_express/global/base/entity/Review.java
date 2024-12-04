package com.currency.turkey_express.global.base.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "point", nullable = false)
    private int point;          // 별점 점수(1 ~ 5 범위)

    @Column(name = "contents", nullable = false, length = 255)
    private String contents;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;


    public Review(Order order, int point, String contents, LocalDateTime createAt) {
        this.order = order;
        this.point = point;
        this.contents = contents;
        this.createAt = LocalDateTime.now();
    }

    public Review() {
        this.createAt = LocalDateTime.now();
    }


}
