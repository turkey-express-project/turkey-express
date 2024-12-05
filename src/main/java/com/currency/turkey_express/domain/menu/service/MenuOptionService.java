package com.currency.turkey_express.domain.menu.service;

import com.currency.turkey_express.domain.menu.dto.MenuSubCategoryRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuTopCategoryRequestDto;
import com.currency.turkey_express.domain.menu.repository.MenuRepository;
import com.currency.turkey_express.domain.menu.repository.MenuSubCategoryRepository;
import com.currency.turkey_express.domain.menu.repository.MenuTopCategoryRepository;
import com.currency.turkey_express.global.base.entity.Menu;
import com.currency.turkey_express.global.base.entity.MenuSubCategory;
import com.currency.turkey_express.global.base.entity.MenuTopCategory;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MenuOptionService {

    private final MenuRepository menuRepository;
    private final MenuTopCategoryRepository menuTopCategoryRepository;
    private final MenuSubCategoryRepository menuSubCategoryRepository;

    public MenuOptionService(MenuRepository menuRepository,
                             MenuTopCategoryRepository menuTopCategoryRepository,
                             MenuSubCategoryRepository menuSubCategoryRepository) {
        this.menuRepository = menuRepository;
        this.menuTopCategoryRepository = menuTopCategoryRepository;
        this.menuSubCategoryRepository = menuSubCategoryRepository;
    }

    // 1. MenuTopCategory 생성
    public MenuTopCategory createTopCategory(Long menuId, MenuTopCategoryRequestDto menuTopCategoryRequestDto) {

//        if (user.getUserType() != UserType.OWNER) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사장님 전용 메뉴입니다.");
//            }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        MenuTopCategory menuTopCategory = new MenuTopCategory(menu, menuTopCategoryRequestDto.getTitle(), menuTopCategoryRequestDto.getStatus());

        return menuTopCategoryRepository.save(menuTopCategory);

    }


    // 2. MenuSubCategory 생성
    public MenuSubCategory createSubCategory(Long menuTopCategoryId, MenuSubCategoryRequestDto menuSubCategoryRequestDto) {

//        if (user.getUserType() != UserType.OWNER) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사장님 전용 메뉴입니다.");
//        }

        MenuTopCategory topCategory = menuTopCategoryRepository.findById(menuTopCategoryId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        MenuSubCategory menuSubCategory = new MenuSubCategory(topCategory, menuSubCategoryRequestDto.getContent(), menuSubCategoryRequestDto.getExtraPrice());

        return menuSubCategoryRepository.save(menuSubCategory);

    }


    // 3. 상위 카테고리 단일 조회
    public MenuTopCategory getTopCategory(Long menuId, Long topCategoryId) {

        menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        return menuTopCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

    }


    // 4. 하위 카테고리 단일 조회
    public MenuSubCategory getSubCategory(Long menuId, Long topCategoryId, Long subCategoryId) {

        menuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "생성된 메뉴가 없습니다."));

        menuTopCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상위 카테고리가 존재하지 않습니다."));

        return menuSubCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "하위 카테고리가 존재하지 않습니다."));

    }
}