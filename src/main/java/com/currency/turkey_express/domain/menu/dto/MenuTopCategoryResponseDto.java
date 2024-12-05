package com.currency.turkey_express.domain.menu.dto;

import com.currency.turkey_express.global.base.enums.memu.NecessaryStatus;
import lombok.Getter;

@Getter
public class MenuTopCategoryResponseDto {

    private Long id;

    private String title;               // 토핑 선택

    private NecessaryStatus status;     // 선택 필수 여부(옵션: OPTIONAL,REQUIRED)

    public MenuTopCategoryResponseDto(Long id, String title, NecessaryStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }
}

