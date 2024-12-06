package com.currency.turkey_express.domain.review.dto;

import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    private int point;          // 별점 (1~5 사이)

    private String comment;     // 리뷰 내용


    public ReviewRequestDto(int point, String comment) {
        if (point < 1 || point > 5) {
            throw new BusinessException(ExceptionType.INVALID_RATING);
        }
        this.point = point;
        this.comment = comment;
    }
}