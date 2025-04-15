package com.chalkboard.chums.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chalkboard.chums.dto.PDFNoteRequest;
import com.chalkboard.chums.exception.ResourceNotFoundException;
import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.model.Tag;
import com.chalkboard.chums.model.User;
import com.chalkboard.chums.repository.PDFNoteRepository;
import com.chalkboard.chums.repository.TagRepository;
import com.chalkboard.chums.repository.UserRepository;

@Service
public class PDFNoteService {

	@Autowired
	private PDFNoteRepository pdfNoteRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private TagRepository tagRepository; 
	
	public List<PDFNote> getAllNotes(){
		return pdfNoteRepository.findAll();
	}
	
	public PDFNote createNote(PDFNoteRequest request) {
		User uploader = userRepository.findById(request.getUploaderId())
				.orElseThrow(() -> new RuntimeException("User not find"));
		
		List<Tag> tags = tagRepository.findAllById(request.getTagIds());
		
		PDFNote note = PDFNote.builder()
				.title(request.getTitle())
				.filePath(request.getFilePath())
				.uploader(uploader)
				.tags(Set.copyOf(tags))
				.visibility(request.getVisibility())
				.build();
		
		return pdfNoteRepository.save(note);
		
	}
	
	public PDFNote getNoteById(Long noteId) {
		return pdfNoteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("ノートID " + noteId + "は存在しません"));
	}
	

}
