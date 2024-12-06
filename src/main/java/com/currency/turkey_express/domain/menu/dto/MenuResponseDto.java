package com.currency.turkey_express.domain.menu.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MenuResponseDto {

    private Long menuId;

    private String menuName;

    private BigDecimal menuPrice;

    private String menuStatus;

    private String menuImage;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    public MenuResponseDto(Long menuId, String menuName, BigDecimal menuPrice, String menuStatus,
                           String menuImage, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuStatus = menuStatus;
        this.menuImage = menuImage;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
