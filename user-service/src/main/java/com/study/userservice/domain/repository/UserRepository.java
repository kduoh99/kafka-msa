package com.study.userservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.userservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
