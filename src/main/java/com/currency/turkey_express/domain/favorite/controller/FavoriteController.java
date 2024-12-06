package com.currency.turkey_express.domain.favorite.controller;

import com.currency.turkey_express.domain.favorite.dto.FavoriteResponseDto;
import com.currency.turkey_express.domain.favorite.service.FavoriteService;
import com.currency.turkey_express.global.annotation.LoginRequired;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.constant.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class FavoriteController {

	private final FavoriteService favoriteService;

	//즐겨찾기 추가
	@LoginRequired
	@PostMapping("/stores/{storeId}/favorites")
	public ResponseEntity<MessageDto> createFavorite(@PathVariable Long storeId, HttpServletRequest request) {
		//로그인 세션 user로 가져옴
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(Const.LOGIN_USER);

		//전달받은 user와 store를 service로 전달
		MessageDto messageDto = favoriteService.createFavorite(storeId, user.getId());
		return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
	}

	//즐겨찾기 삭제
	@LoginRequired
	@DeleteMapping("/stores/{storeId}/favorites")
	public ResponseEntity<MessageDto> deleteFavorite(@PathVariable Long storeId, HttpServletRequest request) {
		//로그인 세션 user로 가져옴
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(Const.LOGIN_USER);

		//전달받은 user와 store를 service로 전달
		MessageDto messageDto = favoriteService.deleteFavorite(storeId, user.getId());
		return new ResponseEntity<>(messageDto, HttpStatus.OK);
	}

	//즐겨찾기 목록 조회(가게 이름만 출력)
	@LoginRequired
	@GetMapping("/users/favorites")
	public ResponseEntity<List<FavoriteResponseDto>> getStoreByFavorite(HttpServletRequest request) {
		//로그인 세션 user로 가져옴
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(Const.LOGIN_USER);

		//전달받은 user를 service로 전달
		List<FavoriteResponseDto> favoriteResponseDtoList = favoriteService.findByUserId(user.getId());

		return new ResponseEntity<>(favoriteResponseDtoList,HttpStatus.OK);
	}



}
