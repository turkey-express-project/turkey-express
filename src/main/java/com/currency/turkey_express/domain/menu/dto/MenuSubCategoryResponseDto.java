package com.currency.turkey_express.domain.menu.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuSubCategoryResponseDto {

    private Long id;

    private String content;             // 추가 사항

    private BigDecimal extraPrice;

    public MenuSubCategoryResponseDto(Long id, String content, BigDecimal extraPrice) {
        this.id = id;
        this.content = content;
        this.extraPrice = extraPrice;
    }
}