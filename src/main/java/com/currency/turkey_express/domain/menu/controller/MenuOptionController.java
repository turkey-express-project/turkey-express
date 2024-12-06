package com.currency.turkey_express.domain.menu.controller;

import com.currency.turkey_express.domain.menu.dto.MenuSubCategoryRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuSubCategoryResponseDto;
import com.currency.turkey_express.domain.menu.dto.MenuTopCategoryRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuTopCategoryResponseDto;
import com.currency.turkey_express.domain.menu.service.MenuOptionService;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.constant.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores/{storeId}/menus/{menuId}/topCategories")
public class MenuOptionController {

    private final MenuOptionService menuOptionService;

    public MenuOptionController(MenuOptionService menuOptionService) {
        this.menuOptionService = menuOptionService;
    }

    /**
     * 상위 카테고리 생성
     * - 사장님만 사용 가능
     * - 예외 처리:
     *   1. 사용자가 사장님이 아닌 경우
     *   2. 메뉴가 없는 경우
     */
    @PostMapping
    public ResponseEntity<MenuTopCategoryResponseDto> createTopCategory(
            @PathVariable Long menuId,
            @RequestBody MenuTopCategoryRequestDto menuTopCategoryRequestDto,
            @SessionAttribute(Const.LOGIN_USER) User loginUser) {

        MenuTopCategoryResponseDto MenuTopCategoryResponseDto = menuOptionService.createTopCategory(menuId, menuTopCategoryRequestDto, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(MenuTopCategoryResponseDto);

    }


    /**
     * 하위 카테고리 생성
     * - 사장님만 사용 가능
     * - 예외 처리:
     *   1. 사용자가 사장님이 아닌 경우
     *   2. 상위 카테고리가 없는 경우
     */
    @PostMapping("/{topCategoryId}/subCategories")
    public ResponseEntity<MenuSubCategoryResponseDto> createSubCategory(
            @PathVariable Long topCategoryId,
            @RequestBody MenuSubCategoryRequestDto menuSubCategoryRequestDto,
            @SessionAttribute(Const.LOGIN_USER) User loginUser) {

        MenuSubCategoryResponseDto menuSubCategoryResponseDto = menuOptionService.createSubCategory(topCategoryId, menuSubCategoryRequestDto, loginUser);

        return ResponseEntity.status((HttpStatus.CREATED)).body(menuSubCategoryResponseDto);

    }

    /**
     * 상위 카테고리 조회
     * - 특정 상위 카테고리를 확인
     */
    @GetMapping("/{topCategoryId}")
    public ResponseEntity<MenuTopCategoryResponseDto> getTopCategory(
            @PathVariable Long menuId,
            @PathVariable Long topCategoryId) {

        MenuTopCategoryResponseDto menuTopCategoryResponseDto = menuOptionService.getTopCategory(menuId, topCategoryId);

        return ResponseEntity.status(HttpStatus.OK).body(menuTopCategoryResponseDto);

    }

    /**
     * 하위 카테고리 조회
     * - 특정 하위 카테고리를 확인
     */
    @GetMapping("/{topCategoryId}/subCategories/{subCategoryId}")
    public ResponseEntity<MenuSubCategoryResponseDto> getSubCategory(
            @PathVariable Long menuId,
            @PathVariable Long topCategoryId,
            @PathVariable Long subCategoryId) {

        MenuSubCategoryResponseDto menuSubCategoryResponseDto = menuOptionService.getSubCategory(menuId, topCategoryId, subCategoryId);

        return ResponseEntity.status(HttpStatus.OK).body(menuSubCategoryResponseDto);

    }
}
