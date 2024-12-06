package com.currency.turkey_express.domain.point.dto;

import com.currency.turkey_express.global.base.entity.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointResponseDto {

	private Long id;//포인트 id

	private Long userId;//사용자 id(외래키)

	private BigDecimal point; //적립금액

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt; //쿠폰 생성일

	private LocalDateTime endDate;//쿠폰 만료일자

	public PointResponseDto(Long id, Long userId, BigDecimal point, LocalDateTime createdAt,
		LocalDateTime endDate) {
		this.id = id;
		this.userId = userId;
		this.point = point;
		this.createdAt = createdAt;
		this.endDate = endDate;
	}

	public static PointResponseDto toDto(Point point) {
		return new PointResponseDto(
			point.getId(),
			point.getUser().getId(),
			point.getPoint(),
			point.getCreatedAt(),
			point.getEndDate()
		);
	}

}
