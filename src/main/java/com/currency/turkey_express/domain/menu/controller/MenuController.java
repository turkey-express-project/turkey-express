package com.currency.turkey_express.domain.menu.controller;

import com.currency.turkey_express.domain.menu.dto.MenuRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuResponseDto;
import com.currency.turkey_express.domain.menu.service.MenuService;
import com.currency.turkey_express.global.annotation.LoginRequired;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.constant.Const;
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

    /**
     * 메뉴 생성
     * - 사장님만 메뉴를 생성 가능
     * - 예외 처리:
     *   1. 사용자가 로그인하지 않은 경우
     *   2. 사용자가 사장님이 아닌 경우
     *   3. 지정된 스토어가 없는 경우
     */
        @LoginRequired
        @PostMapping
        public ResponseEntity<MenuResponseDto> createMenu(
                @PathVariable Long storeId,
                @RequestBody MenuRequestDto menuRequestDto,
                @SessionAttribute(Const.LOGIN_USER) User loginUser) {

            MenuResponseDto menuResponseDto = menuService.createMenu(storeId, menuRequestDto, loginUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);

    }

    /**
     * 메뉴 수정
     * - 사장님만 메뉴를 수정 가능
     * - 예외 처리:
     *   1. 사용자가 로그인하지 않은 경우
     *   2. 사용자가 사장님이 아닌 경우
     *   3. 메뉴가 없는 경우
     */
    @LoginRequired
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto menuRequestDto,
            @SessionAttribute(Const.LOGIN_USER) User loginUser) {

        MenuResponseDto menuResponseDto = menuService.updateMenu(menuId, menuRequestDto, loginUser);

        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);

    }

    /**
     * 메뉴 삭제
     * - 사장님만 메뉴를 삭제 가능
     * - 예외 처리:
     *   1. 사용자가 로그인하지 않은 경우
     *   2. 사용자가 사장님이 아닌 경우
     *   3. 메뉴가 없는 경우
     */
    @LoginRequired
    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable Long menuId,
            @SessionAttribute(Const.LOGIN_USER) User loginUser) {

        menuService.deleteMenu(menuId, loginUser);

        return ResponseEntity.ok("메뉴가 성공적으로 삭제되었습니다.");

    }
}
