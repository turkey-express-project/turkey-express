package com.currency.turkey_express.domain.review.service;

import com.currency.turkey_express.domain.order.repository.OrderRepository;
import com.currency.turkey_express.domain.review.dto.ReviewResponseDto;
import com.currency.turkey_express.domain.review.dto.ReviewRequestDto;
import com.currency.turkey_express.domain.review.dto.StoreReviewStatsResponseDto;
import com.currency.turkey_express.domain.review.repository.ReviewRepository;
import com.currency.turkey_express.global.base.entity.Order;
import com.currency.turkey_express.global.base.entity.Review;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    // 1. 리뷰 작성
    public ReviewResponseDto createReview(String orderGroupIdentifier, ReviewRequestDto reviewRequestDto) {

        // 중복 리뷰 조회
        if(reviewRepository.existsByOrderGroupIdentifier(orderGroupIdentifier)) {
            throw new BusinessException(ExceptionType.DUPLICATE_REVIEW);
        }
        // Order 그룹 조회
        List<Order> orders = orderRepository.findByOrderGroupIdentifier(orderGroupIdentifier);
        if (orders.isEmpty()) {
            throw new BusinessException(ExceptionType.ORDER_NOT_FOUND);
        }
        // 첫 번째 주문 정보로 리뷰 생성
        Order order = orders.get(0);
        Review review = new Review(order, reviewRequestDto.getPoint(), reviewRequestDto.getComment(), orderGroupIdentifier);
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(
                savedReview.getId(),
                savedReview.getPoint(),
                savedReview.getContents(),
                savedReview.getCreateAt()
        );
    }

    // 2. 특정 스토어 모든 리뷰 조회
    public StoreReviewStatsResponseDto getStoreReviewStats(Long storeId) {

        List<ReviewResponseDto> reviews = reviewRepository.findAllByStoreIdOrderByCreateAtDesc(storeId).stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getPoint(),
                        review.getContents(),
                        review.getCreateAt()
                ))
                .collect(Collectors.toList());

        // 리뷰 수 계산
        Long reviewCount = reviewRepository.countReviewsByStoreId(storeId);
        // 평균 별점 계산
        Double averagePoint = reviewRepository.calculateAveragePointByStoreId(storeId);

        return new StoreReviewStatsResponseDto(
                reviewCount,
                averagePoint.longValue(),
                reviews
        );
    }

    // 3. 별점 범위로 리뷰 조회
    public List<ReviewResponseDto> getReviewsByPointRange(Long storeId, int min, int max) {
        return reviewRepository.findReviewsByPointRange(storeId, min, max).stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getPoint(),
                        review.getContents(),
                        review.getCreateAt()
                ))
                .collect(Collectors.toList());
    }
}