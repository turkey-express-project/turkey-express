package com.currency.turkey_express.domain.menu.service;

import com.currency.turkey_express.domain.menu.dto.MenuSubCategoryRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuSubCategoryResponseDto;
import com.currency.turkey_express.domain.menu.dto.MenuTopCategoryRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuTopCategoryResponseDto;
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
import org.springframework.stereotype.Service;

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

    /**
     * 상위 카테고리 생성
     * - 메뉴가 존재하는지 확인
     * - 사장님인지 확인
     * - 예외 처리:
     *   1. 메뉴가 없는 경우
     *   2. 사장님이 아닌 경우
     */
    public MenuTopCategoryResponseDto createTopCategory(Long menuId, MenuTopCategoryRequestDto menuTopCategoryRequestDto, User user) {
        if (user.getUserType() != UserType.OWNER) {
                throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
            }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        MenuTopCategory menuTopCategory = new MenuTopCategory(menu, menuTopCategoryRequestDto.getTitle(), menuTopCategoryRequestDto.getStatus());

        menuTopCategoryRepository.save(menuTopCategory);

        return new MenuTopCategoryResponseDto(menuTopCategory.getId(), menuTopCategory.getTitle(), menuTopCategory.getNecessary());

    }


    /**
     * 하위 카테고리 생성
     * - 상위 카테고리가 존재하는지 확인
     * - 사장님인지 확인
     * - 예외 처리:
     *   1. 상위 카테고리가 없는 경우
     *   2. 사장님이 아닌 경우
     */
    public MenuSubCategoryResponseDto createSubCategory(Long menuTopCategoryId, MenuSubCategoryRequestDto menuSubCategoryRequestDto, User user) {
        if (user.getUserType() != UserType.OWNER) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
        }

        MenuTopCategory topCategory = menuTopCategoryRepository.findById(menuTopCategoryId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        MenuSubCategory menuSubCategory = new MenuSubCategory(topCategory, menuSubCategoryRequestDto.getContent(), menuSubCategoryRequestDto.getExtraPrice());

        menuSubCategoryRepository.save(menuSubCategory);

        return new MenuSubCategoryResponseDto(menuSubCategory.getId(),menuSubCategory.getContent(),menuSubCategory.getExtraPrice());

    }


    /**
     * 상위 카테고리 조회
     * - 메뉴와 카테고리가 존재하는지 확인
     */
    public MenuTopCategoryResponseDto getTopCategory(Long menuId, Long topCategoryId) {

        menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        MenuTopCategory MenuTopCategory = menuTopCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new BusinessException(ExceptionType.TOP_CATEGORY_NOT_FOUND));

        return new MenuTopCategoryResponseDto(MenuTopCategory.getId(), MenuTopCategory.getTitle(), MenuTopCategory.getNecessary());

    }


    /**
     * 하위 카테고리 조회
     * - 메뉴, 상위 카테고리, 하위 카테고리가 존재하는지 확인
     */
    public MenuSubCategoryResponseDto getSubCategory(Long menuId, Long topCategoryId, Long subCategoryId) {

        menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        menuTopCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new BusinessException(ExceptionType.TOP_CATEGORY_NOT_FOUND));

        MenuSubCategory menuSubCategory = menuSubCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new BusinessException(ExceptionType.SUB_CATEGORY_NOT_FOUND));

        return new MenuSubCategoryResponseDto(menuSubCategory.getId(), menuSubCategory.getContent(), menuSubCategory.getExtraPrice());

    }
}