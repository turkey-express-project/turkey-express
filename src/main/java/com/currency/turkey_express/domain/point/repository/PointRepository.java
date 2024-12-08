package com.currency.turkey_express.domain.point.repository;

import com.currency.turkey_express.global.base.entity.Point;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

	List<Point> findAllByUserId(Long userId);

	//합산한 포인트가 null일 경우 0 반환
	@Query("SELECT COALESCE(SUM(p.point), 0) "
		+ "FROM Point p "
		+ "WHERE p.user.id = ?1")
	BigDecimal getTotalPointsByUserId(Long userId);

}
