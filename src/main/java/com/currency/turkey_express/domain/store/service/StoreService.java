package com.currency.turkey_express.domain.store.service;

import com.currency.turkey_express.domain.favorite.repository.FavoriteRepository;
import com.currency.turkey_express.domain.menu.dto.MenuInStoreResponseDto;
import com.currency.turkey_express.domain.menu.repository.MenuRepository;
import com.currency.turkey_express.domain.store.dto.StoreMenuResponseDto;
import com.currency.turkey_express.domain.store.dto.StoreRequestDto;
import com.currency.turkey_express.domain.store.dto.StoreResponseDto;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.store.Category;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final MenuRepository menuRepository;
	private final FavoriteRepository favoriteRepository;

	/*
	* 가게 생성 api
	* - 트랜잭션
	* - 예외처리 :
	*   1. 유저 존재, 유저 탈퇴 여부 확인
	*/
	@Transactional
	public StoreResponseDto createStore(StoreRequestDto dto, Long userId) {
		Store store = new Store(
			dto.getStoreName(),dto.getOpenTime(),dto.getCloseTime(),dto.getCategory(),dto.getOrderAmount()
		);
		User user = findUserOrElseThrow(userId);

		store.setUser(user);
		isFullStores(user);
		storeRepository.save(store);
		return new StoreResponseDto(store);
	}

	/*
	 * 가게 수정 api
	 * - 트랜잭션
	 * - 예외처리 :
	 *   1. 유저 존재, 유저 탈퇴 여부 확인
	 *   2. 가게 존재, 가게 폐업 여부 확인
	 */
	@Transactional
	public StoreResponseDto updateStore(Long storeId, Long userId, StoreRequestDto dto) {
		User user = findUserOrElseThrow(userId);
		Store store = findStoreOrElseThrow(storeId);

		store.setStore(dto);
		store.setUser(user);
		storeRepository.save(store);
		return new StoreResponseDto(store);
	}


	/*
	 * 가게 다건 조회(필터) api
	 * - 예외처리 :
	 *   1. 가게 목록이 null 일 경우 확인
	 */
	public List<StoreResponseDto> findByFilters(String name, Category category, Long minReviews, Double minRating) {
		List<StoreResponseDto> storeList;
		if(name != null || category != null || minReviews != null || minRating != null){
			storeList = storeRepository.findByFilters(name, category, minReviews, minRating);
		}else {
			storeList = storeRepository.findAllStores();
		}
		if(storeList.isEmpty()){
			throw new BusinessException(ExceptionType.STORE_NOT_FOUND);
		}
		return storeList;
	}

	/*
	 * 가게 단건 조회(필터) api
	 * - 예외처리 :
	 *   1. 가게 존재, 가게 폐업 여부 확인
	 *   2. 메뉴 존재 여부 확인
	 */
	public StoreMenuResponseDto findByStoreIdInMenus(Long storeId) {
		Store store = findStoreOrElseThrow(storeId);
		//favoriteRepository 에서 즐겨찾기 개수를 가져옴
		Long favoriteCount = favoriteRepository.countByStoreId(storeId);
		//menuRepository 가게에 존재하는 메뉴 리스트를 가져옴
		List<MenuInStoreResponseDto> menuInStoreResponsDtoList = menuRepository.findAllByStoreId(storeId);
		if(menuInStoreResponsDtoList.isEmpty()){
			throw new BusinessException(ExceptionType.MENU_NOT_FOUND);
		}

		//위의 정보를 통해 반환 dto 생성
		return new StoreMenuResponseDto(store, favoriteCount, menuInStoreResponsDtoList);
	}

	/*
	 * 가게 수정 api
	 * - 트랜잭션
	 * - 예외처리 :
	 *   1. 가게 존재, 가게 폐업 여부 확인
	 *   2. 가게 주인 여부 확인
	 */
	@Transactional
	public StoreResponseDto setCloseStore(Long storeId, Long userId) {
		Store store = findStoreOrElseThrow(storeId);
		if(!store.getUser().getId().equals(userId)){
			throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
		}
		//가게의 상태를 close로 변경
		store.setStoreStatusClose();
		//repository에 저장
		storeRepository.save(store);

		//수정된 정보를 통해 반환 dto 생성
		return new StoreResponseDto(store);
	}

	/***************************************/

	//한 계정당 생성 가능한 가게의 수를 확인
	private void isFullStores(User user){
		int countStores = storeRepository.findAllByUserId(user);
		if(countStores >= 3){
			throw new BusinessException(ExceptionType.ALREADY_FULL_STORE);
		}
	}

	//중복되는 예외처리 코드 메서드로 분리
	private User findUserOrElseThrow(Long userId) {
		//유저 관련 예외처리 (유저가 존재하지 않을 시, 또는 유저가 탈퇴 상태일 시)
		User user = userRepository.findById(userId).orElseThrow(
			() -> new BusinessException(ExceptionType.USER_NOT_FOUND)
		);
		if (user.getUserStatus().equals(UserStatus.DELETE)){
			throw new BusinessException(ExceptionType.DELETED_USER);
		}
		return user;
	}

	private Store findStoreOrElseThrow(Long storeId) {
		//가게 관련 예외처리 (가게가 존재하지 않을 시, 또는 폐업 상태일 시)
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new BusinessException(ExceptionType.STORE_NOT_FOUND)
		);
		if (store.getStoreStatus().equals("CLOSE")){
			throw new BusinessException(ExceptionType.CLOSE_STORE);
		}
		return store;
	}
}
