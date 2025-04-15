package com.chalkboard.chums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chalkboard.chums.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}