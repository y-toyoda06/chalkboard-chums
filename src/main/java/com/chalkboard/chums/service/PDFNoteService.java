package com.chalkboard.chums.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chalkboard.chums.dto.PDFNoteRequest;
import com.chalkboard.chums.exception.ResourceNotFoundException;
import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.model.Tag;
import com.chalkboard.chums.model.User;
import com.chalkboard.chums.model.Visibility;
import com.chalkboard.chums.repository.LikeRepository;
import com.chalkboard.chums.repository.PDFNoteRepository;
import com.chalkboard.chums.repository.TagRepository;
import com.chalkboard.chums.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PDFNoteService {

	@Autowired
	private PDFNoteRepository pdfNoteRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private TagRepository tagRepository; 
	
	@Autowired
	private LikeRepository likeRepository;
	
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
	
	public void saveNote(Long userId, String title, MultipartFile file) throws IOException {
		//ユーザーが存在するかの確認
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("ユーザーが存在しません"));
		
		//保存先のパスを決定
		String uploadDir = System.getProperty("user.dir") + "/uploads/";
		String filePath = uploadDir + UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); //ランダム文字列＋ファイル名
		
		//フォルダがないときは作成
		File dir = new File(uploadDir);
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		//ファイルの保存
		file.transferTo(new File(filePath));
		
		//PDFNoteエンティティを作って保存(記録)
		PDFNote note = new PDFNote();
		note.setTitle(title);
		note.setFilePath(filePath);
		note.setUploader(user);
		note.setVisibility(Visibility.PUBLIC);

		
		pdfNoteRepository.save(note);
		
		
		
	}
	
	@Transactional
	public void deleteNote(Long noteid, Long userId) {
		PDFNote note = pdfNoteRepository.findById(noteid)
				.orElseThrow( () -> new IllegalArgumentException("ノートが見つかりません"));
		
		if (!note.getUploader().getId().equals(userId)) {
		        throw new AccessDeniedException("自分のノートしか削除できません");
		}
		
		// 先にlikesを削除
	    likeRepository.deleteByPdfNoteId(noteid);
		
		File file = new File(note.getFilePath());
		if(file.exists()) {
			file.delete();
		}
		
		
		
		pdfNoteRepository.delete(note);
		
		
	}
	

	
	
	
}
