package com.currency.turkey_express.domain.menu.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuRequestDto {

    private String menuName;

    private BigDecimal menuPrice;

    private String menuImage;

    public MenuRequestDto(String menuName, BigDecimal menuPrice, String menuImage) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
    }

}
