package com.currency.turkey_express.domain.store.entity;

import com.currency.turkey_express.domain.store.enums.StoreStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Time;
import lombok.Getter;

@Getter
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String storeName;

	private Time openTime;
	private Time closeTime;

	@Enumerated(EnumType.STRING)
	private StoreStatus storeStatus;



	//private User user;


}
