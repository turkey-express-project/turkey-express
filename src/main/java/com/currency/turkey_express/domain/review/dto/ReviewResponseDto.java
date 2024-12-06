package com.currency.turkey_express.domain.review.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    // 리뷰 상세 응답 DTO
    private Long reviewId;

    private int point;

    private String contents;

    private LocalDateTime createdAt;

    public ReviewResponseDto(Long reviewId, int point, String contents, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.point = point;
        this.contents = contents;
        this.createdAt = createdAt;
    }

}
