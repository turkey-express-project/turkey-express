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

	//가게 생성 api
	@Transactional
	public StoreResponseDto createStore(StoreRequestDto dto) {
		Store store = new Store(dto.getStoreName(),dto.getOpenTime(),dto.getCloseTime(),dto.getCategory(),dto.getOrderAmount());

		//이 부분은 로그인 세션이 아직 구현되지 않아 임의로 작성한 부분입니다.
		//세션 구현시 세션 아이디를 통해 유저 데이터를 받아올 예정입니다.
		Long id = 1L;
		User user = userRepository.findById(id).orElseThrow(
			()->new RuntimeException("User Not Found")
		);

		store.setUser(user);
		isFullStores(user);
		storeRepository.save(store);
		return new StoreResponseDto(store);
	}

	//가게 수정 api
	@Transactional
	public StoreResponseDto updateStore(Long storeId, StoreRequestDto dto) {
		//이 부분은 로그인 세션이 아직 구현되지 않아 임의로 작성한 부분입니다.
		//세션 구현시 세션 아이디를 통해 유저 데이터를 받아올 예정입니다.
		Long id = 1L;
		User user = userRepository.findById(id).orElseThrow(
			()->new RuntimeException("User Not Found")
		);

		Store store = storeRepository.findById(storeId).orElseThrow(
			()->new RuntimeException("Store Not Found")
		);
		store.setStore(dto);
		store.setUser(user);
		storeRepository.save(store);
		return new StoreResponseDto(store);
	}


	//미완성
//	public List<StoreResponseDto> findByFilters(String name, String category, Long minReviews, Double minRating) {
//		List<StoreResponseDto> storeList;
//		if(name != null || category != null || minReviews != null || minRating != null){
//			storeList = storeRepository.findByFilters(name, category, minReviews, minRating);
//		}else {
//			storeList = storeRepository.findAllStores();
//		}
//		return storeList;
//	}

	//가게 단건 조회 (즐겨찾기 갯수, 메뉴 리스트 함께 반환)
	public StoreMenuResponseDto findByStoreIdInMenus(Long storeId) {
		//예외처리 수정예정
		Store store = storeRepository.findById(storeId).orElseThrow(
			()->new RuntimeException("Store Not Found")
		);
		//favoriteRepository 에서 즐겨찾기 개수를 가져옴
		Long favoriteCount = favoriteRepository.countByStoreId(storeId);
		//menuRepository 가게에 존재하는 메뉴 리스트를 가져옴
		List<MenuInStoreResponseDto> menuInStoreResponsDtoList = menuRepository.findAllByStoreId(storeId);

		//위의 정보를 통해 반환 dto 생성
		return new StoreMenuResponseDto(store, favoriteCount, menuInStoreResponsDtoList);
	}

	//가게 폐업으로 상태 변경
	@Transactional
	public StoreResponseDto setCloseStore(Long storeId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			()->new RuntimeException("Store Not Found")
		);
		//가게의 상태를 close로 변경
		store.setStoreStatusClose();
		//repository에 저장
		storeRepository.save(store);

		//수정된 정보를 통해 반환 dto 생성
		return new StoreResponseDto(store);
	}

	//한 계정당 생성 가능한 가게의 수를 확인
	private void isFullStores(User user){
		int countStores = storeRepository.findAllByUserId(user);
		//글로벌 예외처리 작성시 수정 예정입니다.
		if(countStores >= 3){
			throw new RuntimeException("한 계정 당 3개의 가게까지 생성할 수 있습니다.");
		}
	}


}
