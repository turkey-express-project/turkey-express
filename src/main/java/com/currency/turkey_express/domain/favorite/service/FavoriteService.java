package com.currency.turkey_express.domain.favorite.service;

import com.currency.turkey_express.domain.favorite.dto.FavoriteResponseDto;
import com.currency.turkey_express.domain.favorite.repository.FavoriteRepository;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.currency.turkey_express.global.base.entity.Favorite;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;

	/*
	 * 즐겨찾기 추가 api
	 * - 트랜잭션
	 * - 예외처리 :
	 *   1. 유저 존재, 유저 탈퇴 여부 확인
	 *   2. 가게 존재, 가게 폐업 여부 확인
	 *   3. 즐겨찾기 한 유저가 가게 오너 유저와 같은지 확인
	 */
	@Transactional
	public MessageDto createFavorite(Long storeId, Long userId) {
		Store store = findStoreOrElseThrow(storeId);
		User user = findUserOrElseThrow(userId);

		//자신의 가게에 즐겨찾기 시 예외처리
		if(store.getUser().getId().equals(user.getId())){
			throw new BusinessException(ExceptionType.SELF_ADD_FAVORITE);
		}

		//즐겨찾기 추가
		Favorite favorite = new Favorite(user, store);
		favoriteRepository.save(favorite);
		return new MessageDto("즐겨찾기에 추가되었습니다.");
	}

	/*
	 * 즐겨찾기 삭제 api
	 * - 트랜잭션
	 * - 예외처리 :
	 *   1. 유저 존재, 유저 탈퇴 여부 확인
	 *   2. 가게 존재, 가게 폐업 여부 확인
	 *   3. 즐겨찾기 존재 여부 확인
	 */
	@Transactional
	public MessageDto deleteFavorite(Long storeId, Long userId) {
		Store store = findStoreOrElseThrow(storeId);
		User user = findUserOrElseThrow(userId);

		//Favorite 조회 및 삭제
		Favorite favorite = favoriteRepository.findByStoreIdAndUserId(storeId,userId);

		if(favorite == null){
			throw new BusinessException(ExceptionType.FAVORITE_NOT_FOUND);
		}

		favoriteRepository.delete(favorite);
		return new MessageDto("즐겨찾기에서 삭제되었습니다.");
	}

	/*
	 * 즐겨찾기 조회 api
	 * - 트랜잭션
	 * - 예외처리 :
	 *   1. 즐겨찾기 존재 여부 확인
	 */
	public List<FavoriteResponseDto> findByUserId(Long userId) {
		List<FavoriteResponseDto> favoriteResponseDtoList = favoriteRepository.findAllByUserId(userId);
		if (favoriteResponseDtoList.isEmpty()) {
			throw new BusinessException(ExceptionType.FAVORITE_NOT_FOUND);
		}
		return favoriteResponseDtoList;
	}

	/********************중복되는 예외처리 코드 메서드로 분리*****************************/


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
