package com.chalkboard.chums.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}/notes")
	public List<PDFNote> getUserNotes(@PathVariable Long userId){
		return userService.getNoteByUserId(userId);
	}
}
