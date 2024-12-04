package com.currency.turkey_express.domain.store.repository;

import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

	//jPQL 사용해서 유저의 가게 개수를 count 합니다
	@Query(
		"SELECT COUNT(s) FROM Store AS s WHERE s.user = :user"
	)
	int findAllByUserId(@Param("user") User user);
}
