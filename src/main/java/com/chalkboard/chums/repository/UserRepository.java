package com.chalkboard.chums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chalkboard.chums.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
