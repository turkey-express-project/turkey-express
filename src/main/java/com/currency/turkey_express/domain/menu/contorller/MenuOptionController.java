package com.currency.turkey_express.domain.menu.contorller;

import com.currency.turkey_express.domain.menu.dto.MenuSubCategoryRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuTopCategoryRequestDto;
import com.currency.turkey_express.domain.menu.service.MenuOptionService;
import com.currency.turkey_express.global.base.entity.MenuSubCategory;
import com.currency.turkey_express.global.base.entity.MenuTopCategory;
import com.currency.turkey_express.global.base.entity.User;
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

    // 1. MenuTopCategory 생성
    @PostMapping
    public ResponseEntity<MenuTopCategory> createTopCategory(
            @PathVariable Long menuId,
            @RequestBody MenuTopCategoryRequestDto menuTopCategoryRequestDto) {

        MenuTopCategory MenuTopCategory = menuOptionService.createTopCategory(menuId, menuTopCategoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(MenuTopCategory);

    }


    // 2. MenuSubCategory 생성
    @PostMapping("/{topCategoryId}/subCategories")
    public ResponseEntity<MenuSubCategory> createSubCategory(
            @PathVariable Long topCategoryId,
            @RequestBody MenuSubCategoryRequestDto menuSubCategoryRequestDto) {

        MenuSubCategory menuSubCategory = menuOptionService.createSubCategory(topCategoryId, menuSubCategoryRequestDto);

        return ResponseEntity.status((HttpStatus.CREATED)).body(menuSubCategory);

    }


    // 3. 상위 카테고리 단일 조회
    @GetMapping("/{topCategoryId}")
    public ResponseEntity<MenuTopCategory> getTopCategory(
            @PathVariable Long menuId,
            @PathVariable Long topCategoryId) {

        MenuTopCategory MenuTopCategory = menuOptionService.getTopCategory(menuId, topCategoryId);

        return ResponseEntity.status(HttpStatus.OK).body(MenuTopCategory);

    }


    // 4. 하위 카테고리 단일 조회
    @GetMapping("/{topCategoryId}/subCategories/{subCategoryId}")
    public ResponseEntity<MenuSubCategory> getSubCategory(
            @PathVariable Long menuId,
            @PathVariable Long topCategoryId,
            @PathVariable Long subCategoryId) {

        MenuSubCategory menuSubCategory = menuOptionService.getSubCategory(menuId, topCategoryId, subCategoryId);

        return ResponseEntity.status(HttpStatus.OK).body(menuSubCategory);

    }
}
