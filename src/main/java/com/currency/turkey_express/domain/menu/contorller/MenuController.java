package com.currency.turkey_express.domain.menu.contorller;

import com.currency.turkey_express.domain.menu.dto.MenuRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuResponseDto;
import com.currency.turkey_express.domain.menu.service.MenuService;
import com.currency.turkey_express.global.base.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores/{storeId}/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // 1. 메뉴 생성
        @PostMapping
        public ResponseEntity<MenuResponseDto> createMenu(
                @PathVariable Long storeId,
                @RequestBody MenuRequestDto menuRequestDto) {

            MenuResponseDto menuResponseDto = menuService.createMenu(storeId, menuRequestDto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(menuResponseDto);

    }


    // 2. 메뉴 수정
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto menuRequestDto) {

        MenuResponseDto menuResponseDto = menuService.updateMenu(menuId, menuRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuResponseDto);

    }


    // 3. 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable Long menuId) {

        menuService.deleteMenu(menuId);

        return ResponseEntity.ok("메뉴가 성공적으로 삭제되었습니다.");

    }
}