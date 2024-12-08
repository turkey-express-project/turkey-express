package com.currency.turkey_express.domain.store.repository;

import com.currency.turkey_express.domain.store.dto.StoreResponseDto;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.store.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	/*
	* 유저의 가게 개수를 count해 유저가 운영중인 가게의 개수 반환
	* 유저 가게 개수 예외처리 검토때 사용
	*/
	@Query(
		"SELECT COUNT(s) FROM Store AS s WHERE s.user = :user AND s.storeStatus = 'OPEN'"
	)
	int findAllByUserId(@Param("user") User user);

	/*
	* 조건에 맞는 가게를 즐겨찾기 많은 순으로 정렬해 리스트로 반환
	* 가게 다건 조회 api에 필터가 존재할 때 api에 사용
	* - 조건 : 이름, 카테고리, 최소 리뷰수, 최소 별점 평균
	*/
	@Query(
		"SELECT new com.currency.turkey_express.domain.store.dto.StoreResponseDto(s) "+
			"FROM Store AS s "+
			"LEFT JOIN Review r ON r.order.store.id = s.id "+
			"WHERE s.storeStatus = 'OPEN' "+
			"AND (:name IS NULL OR s.storeName LIKE %:name%) "+
			"AND (:category IS NULL OR s.category = :category) "+
			"GROUP BY s.id "+
			"HAVING (:minReviews IS NULL OR COUNT(r) >= :minReviews) "+
			"AND (:minRating IS NULL OR AVG(r.point) >= :minRating) "+
			"ORDER BY COUNT(r) DESC"
	)
	List<StoreResponseDto> findByFilters(String name, Category category, Long minReviews, Double minRating);

	/*
	* 전체 가게 목록을 즐겨찾기 많은 순으로 정렬해 리스트로 반환
	* 가게 다건 조회 api에 필터 조건이 없을 시 사용
	*/
	@Query(
		"SELECT new com.currency.turkey_express.domain.store.dto.StoreResponseDto(s) "+
			"FROM Store AS s "+
			"LEFT JOIN Review r ON r.order.store.id = s.id "+
			"WHERE s.storeStatus = 'OPEN' "+
			"GROUP BY s.id "+
			"ORDER BY COUNT(r) DESC"
	)
	List<StoreResponseDto> findAllStores();


}
