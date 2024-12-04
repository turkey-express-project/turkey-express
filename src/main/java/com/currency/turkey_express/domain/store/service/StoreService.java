package com.currency.turkey_express.domain.store.service;

import com.currency.turkey_express.domain.store.dto.StoreRequestDto;
import com.currency.turkey_express.domain.store.dto.StoreResponseDto;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;

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

	//한 계정당 생성 가능한 가게의 수를 확인
	private void isFullStores(User user){
		int countStores = storeRepository.findAllByUserId(user);
		//글로벌 예외처리 작성시 수정 예정입니다.
		if(countStores >= 3){
			throw new RuntimeException("한 계정 당 3개의 가게까지 생성할 수 있습니다.");
		}
	}

}
