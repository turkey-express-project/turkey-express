package com.currency.turkey_express.domain.store.controller;

import com.currency.turkey_express.domain.store.dto.StoreRequestDto;
import com.currency.turkey_express.domain.store.dto.StoreResponseDto;
import com.currency.turkey_express.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto dto){
		StoreResponseDto storeResponseDto = storeService.createStore(dto);
		return new ResponseEntity<>(storeResponseDto, HttpStatus.CREATED);
	}

	@PatchMapping("/{store_id}")
	public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long store_id, @RequestBody StoreRequestDto dto) {
		StoreResponseDto storeResponseDto = storeService.updateStore(store_id, dto);
		return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
	}
}
