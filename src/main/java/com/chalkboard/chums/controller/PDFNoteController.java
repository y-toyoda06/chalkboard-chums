package com.chalkboard.chums.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chalkboard.chums.dto.PDFNoteRequest;
import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.service.PDFNoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class PDFNoteController {

	@Autowired
	private PDFNoteService pdfNoteService;
	
	//全ノート一覧
	@GetMapping("/notes")
	public List<PDFNote> getAllNotes() {
		return pdfNoteService.getAllNotes();
	}
	
	@PostMapping
	public PDFNote createNote(@RequestBody @Valid PDFNoteRequest request) {
	    return pdfNoteService.createNote(request);
	}
	
	
	//ユーザー指定ノート一覧
	@GetMapping("/notes/{id}")
	public PDFNote getNote(@PathVariable Long id) {
		return pdfNoteService.getNoteById(id);
	}
	
	@PostMapping("/{userId}/notes")
	public ResponseEntity<String> uploadNote(@PathVariable Long userId,
			@RequestParam("title") String title,
			@RequestParam("file") MultipartFile file){
		try {
			pdfNoteService.saveNote(userId, title, file);
			return ResponseEntity.ok("アップロード成功！");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("アップロード失敗: " + e.getMessage() );
		}
		
	}
		
	
		
	@DeleteMapping("/{userId}/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable Long userId,
			@PathVariable Long id){
		pdfNoteService.deleteNote(id, userId);
		return ResponseEntity.ok("削除成功");
	}

	

	
	
	
}
