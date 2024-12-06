package com.currency.turkey_express.domain.review.repository;

import com.currency.turkey_express.global.base.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 스토어의 전체 리뷰 조회(최신순 기준)
    @Query("SELECT r FROM Review r WHERE r.order.store.id = :storeId ORDER BY r.createAt DESC")
    List<Review> findAllByStoreIdOrderByCreateAtDesc(Long storeId);

    // 특정 스토어의 리뷰 수 계산
    @Query("SELECT COUNT(r) FROM Review r WHERE r.order.store.id = :storeId")
    Long countReviewsByStoreId(Long storeId);

    // 특정 스토어의 평균 별점을 계산
    @Query("SELECT COALESCE(AVG(r.point), 0) FROM Review r WHERE r.order.store.id = :storeId")
    Double calculateAveragePointByStoreId(Long storeId);

    // 별점 범위에 맞는 리뷰 불러오기
    @Query("SELECT r FROM Review r WHERE r.order.store.id = :storeId AND r.point BETWEEN :min AND :max")
    List<Review> findReviewsByPointRange(Long storeId, int min, int max);

    // 특정 주문 그룹에 리뷰 존재 여부 확인
    boolean existsByOrderGroupIdentifier(String orderGroupIdentifier);

}