package com.currency.turkey_express.domain.review.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class StoreReviewStatsResponseDto {

    // 가게 리뷰 통계 응답
    private Long reviewCount;                         // 리뷰수

    private Long averagePoint;                        // 평균 별점

    private List<ReviewResponseDto> listReviews;      // 리뷰 목록

    public StoreReviewStatsResponseDto(Long reviewCount, Long averagePoint, List<ReviewResponseDto> listReviews) {
        this.reviewCount = reviewCount;
        this.averagePoint = averagePoint;
        this.listReviews = listReviews;
    }
}
