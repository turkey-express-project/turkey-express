package com.currency.turkey_express.domain.menu.service;

import com.currency.turkey_express.domain.menu.repository.MenuRepository;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.domain.menu.dto.MenuRequestDto;
import com.currency.turkey_express.domain.menu.dto.MenuResponseDto;
import com.currency.turkey_express.global.base.entity.Menu;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.memu.MenuStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    /**
     * 메뉴 생성
     * - 사장님 여부 확인
     * - 본인의 스토어 확인
     * - 예외:
     *   1. 스토어를 찾을 수 없는 경우: "스토어를 찾을 수 없습니다."
     *   2. 본인의 스토어가 아닌 경우: "본인의 스토어만 관리할 수 있습니다."
     */
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto, User user) {
        if (user.getUserType() != UserType.OWNER) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ExceptionType.STORE_NOT_FOUND));

        checkStoreOwnership(store, user);

        Menu menu = new Menu(
                store,
                menuRequestDto.getMenuName(),
                menuRequestDto.getMenuPrice(),
                MenuStatus.REGISTER,
                menuRequestDto.getMenuImage()
        );

        menuRepository.save(menu);

        return new MenuResponseDto(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getStatus().name(),
                menu.getImage(),
                menu.getCreatedAt(),
                menu.getModifiedAt()
        );
    }


    /**
     * 메뉴 수정
     * - 사장님만 수정 가능
     * - 예외:
     *   1. 메뉴를 찾을 수 없는 경우: "메뉴를 찾을 수 없습니다."
     *   2. 본인의 스토어가 아닌 경우: "본인의 스토어만 관리할 수 있습니다."
     */
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto, User user) {
        if (user.getUserType() != UserType.OWNER) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
        }

        // 생성 메뉴 확인
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        // 스토어 소유자 == 현재 사용자 검증
        checkStoreOwnership(menu.getStore(), user);

        // 메뉴 정보 수정
        menu.update(
                menuRequestDto.getMenuName(),
                menuRequestDto.getMenuPrice(),
                MenuStatus.REGISTER,
                menuRequestDto.getMenuImage()
        );

                menuRepository.save(menu);

                return new MenuResponseDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getPrice(),
                        menu.getStatus().name(),
                        menu.getImage(),
                        menu.getCreatedAt(),
                        menu.getModifiedAt()
                );
    }

    /**
     * 메뉴 삭제
     * - 사장님만 삭제 가능
     * - 예외:
     *   1. 메뉴를 찾을 수 없는 경우: "메뉴를 찾을 수 없습니다."
     *   2. 본인의 스토어가 아닌 경우: "본인의 스토어만 관리할 수 있습니다."
     */
    public void deleteMenu(Long menuId, User user) {
        if (user.getUserType() != UserType.OWNER) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        checkStoreOwnership(menu.getStore(), user);

        menu.setStatus(MenuStatus.DELETED);

        menuRepository.save(menu);

    }

    /**
     * 본인의 스토어인지 확인
     */
    private void checkStoreOwnership(Store store, User user) {
        if (!store.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);

        }

    }
}
