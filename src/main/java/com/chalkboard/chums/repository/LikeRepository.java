package com.chalkboard.chums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chalkboard.chums.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
