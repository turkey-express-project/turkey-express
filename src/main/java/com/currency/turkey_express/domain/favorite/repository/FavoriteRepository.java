package com.currency.turkey_express.domain.favorite.repository;

import com.currency.turkey_express.domain.favorite.dto.FavoriteResponseDto;
import com.currency.turkey_express.global.base.entity.Favorite;
import com.currency.turkey_express.global.base.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	@Query(
		"SELECT COUNT(f) "+
			"FROM Favorite AS f WHERE f.store.id = :storeId"
	)
	Long countByStoreId(Long storeId);

	@Query(
		"SELECT f "+
			"FROM Favorite AS f WHERE f.store.id = :storeId AND f.user.id = :userId"
	)
	Favorite findByStoreIdAndUserId(Long storeId, Long userId);

	@Query(
		"SELECT new com.currency.turkey_express.domain.favorite.dto.FavoriteResponseDto(f) "+
			"from Favorite AS f WHERE f.user.id = :userId"
	)
	List<FavoriteResponseDto> findAllByUserId(Long userId);
}
