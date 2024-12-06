package com.currency.turkey_express.domain.menu.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuSubCategoryRequestDto {

    private String content;

    private BigDecimal extraPrice;


    public MenuSubCategoryRequestDto(String content, BigDecimal extraPrice) {
        this.content = content;
        this.extraPrice = extraPrice;
    }
}
