package com.currency.turkey_express.domain.user.repository;

import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	default User findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(
			() -> new BusinessException(ExceptionType.USER_NOT_FOUND));
	}

	Optional<User> findByEmail(String email);

	default User findByEmailOrElseThrow(String email) {
		return findByEmail(email).orElseThrow(
			() -> new BusinessException(ExceptionType.USER_NOT_FOUND));
	}
}
