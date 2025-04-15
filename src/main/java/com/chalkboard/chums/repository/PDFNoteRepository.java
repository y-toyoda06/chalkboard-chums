package com.chalkboard.chums.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chalkboard.chums.model.PDFNote;

public interface PDFNoteRepository extends JpaRepository<PDFNote, Long> {
	
	List<PDFNote> findByUploaderId(Long uploaderId);


}