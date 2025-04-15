package com.chalkboard.chums.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chalkboard.chums.exception.ResourceNotFoundException;
import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.repository.PDFNoteRepository;
import com.chalkboard.chums.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private PDFNoteRepository pdfNoteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<PDFNote> getNoteByUserId(Long userId){
	    // ここでユーザーの存在チェックを追加
	    userRepository.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("ユーザーID " + userId + " は存在しません"));
		return pdfNoteRepository.findByUploaderId(userId);
	}

}
