package com.currency.turkey_express.domain.user.repository;

import com.currency.turkey_express.global.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
