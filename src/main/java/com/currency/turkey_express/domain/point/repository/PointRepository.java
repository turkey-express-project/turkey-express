package com.currency.turkey_express.domain.point.repository;

import com.currency.turkey_express.global.base.entity.Point;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

	List<Point> findAllByUserId(Long userId);
}
