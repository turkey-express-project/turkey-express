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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    // 1. 메뉴 생성
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto) {

//        if (user.getUserType() != UserType.OWNER) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사장님 전용 메뉴입니다.");
//        }

        // 스토어 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ExceptionType.STORE_NOT_FOUND));

        // 본인 스토어 확인
//        checkedStore(store, user);

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


    // 2. 메뉴 수정
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto) {

//        if (user.getUserType() != UserType.OWNER) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사장님 전용 메뉴입니다.");
//        }

        // 생성 메뉴 확인
        Menu menu = (Menu)menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        // 스토어 소유자 == 현재 사용자 검증
//        checkedStore(store, user);

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

    // 3. 메뉴 삭제
    public void deleteMenu(Long menuId) {

//        if (user.getUserType() != UserType.OWNER) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사장님 전용 메뉴입니다.");
//        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MENU_NOT_FOUND));

        menu.setStatus(MenuStatus.DELETED);

        menuRepository.save(menu);

    }

    // 본인 스토어 확인용
//    private void checkedStore(Store store, User user) {
//
//        if (!store.getUser().getId().equals(user.getId())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인 스토어에만 접근할 수 있습니다.");
//        }
//    }

}
