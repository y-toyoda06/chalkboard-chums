package com.chalkboard.chums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chalkboard.chums.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>{

}
