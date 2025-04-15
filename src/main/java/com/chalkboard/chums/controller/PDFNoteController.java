package com.chalkboard.chums.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chalkboard.chums.dto.PDFNoteRequest;
import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.service.PDFNoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
public class PDFNoteController {

	@Autowired
	private PDFNoteService pdfNoteService;
	
	@GetMapping
	public List<PDFNote> getAllNotes() {
		return pdfNoteService.getAllNotes();
	}
	
	@PostMapping
	public PDFNote createNote(@RequestBody @Valid PDFNoteRequest request) {
	    return pdfNoteService.createNote(request);
	}
	
	@GetMapping("/notes/{id}")
	public PDFNote getNote(@PathVariable Long id) {
		return pdfNoteService.getNoteById(id);
	}

}
