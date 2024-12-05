package com.currency.turkey_express.domain.menu.dto;


import com.currency.turkey_express.global.base.enums.memu.NecessaryStatus;
import lombok.Getter;

@Getter
public class MenuTopCategoryRequestDto {

    private String title;

    private NecessaryStatus status;  // 선택 필수 여부(옵션: OPTIONAL,REQUIRED)


    public MenuTopCategoryRequestDto(String title, NecessaryStatus status) {
        this.title = title;
        this.status = status;
    }

}