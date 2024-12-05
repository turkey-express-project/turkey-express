package com.currency.turkey_express.domain.store.repository;

import com.currency.turkey_express.domain.store.dto.StoreResponseDto;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	//jPQL 사용해서 유저의 가게 개수를 count 합니다
	@Query(
		"SELECT COUNT(s) FROM Store AS s WHERE s.user = :user"
	)
	int findAllByUserId(@Param("user") User user);

//	@Query(
//		"SELECT new com.currency.turkey_express.domain.store.dto.StoreResponseDto(s) "+
//			"FROM Store AS s "+
//			"WHERE s.storeStatus = 'OPEN' "+
//			"AND (:name IS NULL OR s.storeName LIKE %:name%) "+
//			"AND (:category IS NULL OR s.category = :category) "+
//			"AND (:minReviews IS NULL OR )"
//	)
//	List<StoreResponseDto> findByFilters(String name, String category, Long minReviews, Double minRating);

	@Query(
		"SELECT new com.currency.turkey_express.domain.store.dto.StoreResponseDto(s) "+
			"FROM Store AS s WHERE s.storeStatus = 'OPEN'"
	)
	List<StoreResponseDto> findAllStores();


}
