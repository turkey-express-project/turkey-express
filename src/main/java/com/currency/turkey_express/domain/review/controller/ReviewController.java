package com.currency.turkey_express.domain.review.controller;

import com.currency.turkey_express.domain.review.dto.ReviewResponseDto;
import com.currency.turkey_express.domain.review.dto.ReviewRequestDto;
import com.currency.turkey_express.domain.review.dto.StoreReviewStatsResponseDto;
import com.currency.turkey_express.domain.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 1. 리뷰 작성
    @PostMapping("/orders/{orderGroupIdentifier}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable String orderGroupIdentifier,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return ResponseEntity.ok(reviewService.createReview(orderGroupIdentifier, reviewRequestDto));
    }

    // 2. 특정 스토어 모든 리뷰 조회
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreReviewStatsResponseDto> getStoreReviewsWithStats(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getStoreReviewStats(storeId));
    }

    // 3. 별점 범위로 리뷰 조회
    @GetMapping("/stores/{storeId}/reviews-range")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByPointRange(
            @PathVariable Long storeId,
            @RequestParam int min,       // 최소 별점
            @RequestParam int max) {     // 최대 별점

        return ResponseEntity.ok(reviewService.getReviewsByPointRange(storeId, min, max));
    }
}