package com.currency.turkey_express.domain.store.controller;

import com.currency.turkey_express.domain.store.dto.StoreMenuResponseDto;
import com.currency.turkey_express.domain.store.dto.StoreRequestDto;
import com.currency.turkey_express.domain.store.dto.StoreResponseDto;
import com.currency.turkey_express.domain.store.service.StoreService;
import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.store.Category;
import com.currency.turkey_express.global.base.enums.user.UserType;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	private final StoreService storeService;

	//가게 생성
	@UserRequired(vaild = UserType.OWNER)
	@PostMapping
	public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto dto, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(Const.LOGIN_USER);
		StoreResponseDto storeResponseDto = storeService.createStore(dto, user.getId());
		return new ResponseEntity<>(storeResponseDto, HttpStatus.CREATED);
	}

	//가게 수정
	@UserRequired(vaild = UserType.OWNER)
	@PatchMapping("/{storeId}")
	public ResponseEntity<StoreResponseDto> updateStore(
		@PathVariable Long storeId, @RequestBody StoreRequestDto dto, HttpServletRequest request
	){
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(Const.LOGIN_USER);
		StoreResponseDto storeResponseDto = storeService.updateStore(storeId, user.getId(), dto);
		return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
	}

	//가게 다건 조회(필터)
	@UserRequired
	@GetMapping
	public ResponseEntity<List<StoreResponseDto>> getAllStores(
		@RequestParam(required = false) String name, @RequestParam(required = false) Category category,
		@RequestParam(required = false) Long min_reviews, @RequestParam(required = false) Double min_rating
	) {
		List<StoreResponseDto> storeList = storeService.findByFilters(name, category, min_reviews, min_rating);
		return new ResponseEntity<>(storeList, HttpStatus.OK);
	}

	//가게 단건 조회
	@UserRequired
	@GetMapping("/{storeId}")
	public ResponseEntity<StoreMenuResponseDto> getStore(@PathVariable Long storeId) {
		StoreMenuResponseDto storeMenuResponseDto = storeService.findByStoreIdInMenus(storeId);
		return new ResponseEntity<StoreMenuResponseDto>(storeMenuResponseDto, HttpStatus.OK);
	}

	//가게 상태 폐업으로 변경
	@UserRequired(vaild = UserType.OWNER)
	@DeleteMapping("/{storeId}")
	public ResponseEntity<StoreResponseDto> closeStore(@PathVariable Long storeId, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(Const.LOGIN_USER);
		StoreResponseDto storeResponseDto = storeService.setCloseStore(storeId, user.getId());
		return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
	}
}
