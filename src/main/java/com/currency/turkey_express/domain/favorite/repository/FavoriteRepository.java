package com.currency.turkey_express.domain.favorite.repository;

import com.currency.turkey_express.global.base.entity.Favorite;
import com.currency.turkey_express.global.base.entity.Store;
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
}
