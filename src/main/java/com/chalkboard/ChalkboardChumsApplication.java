package com.chalkboard;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chalkboard.chums.model.Follow;
import com.chalkboard.chums.model.Like;
import com.chalkboard.chums.model.PDFNote;
import com.chalkboard.chums.model.Tag;
import com.chalkboard.chums.model.TagType;
import com.chalkboard.chums.model.User;
import com.chalkboard.chums.model.Visibility;
import com.chalkboard.chums.repository.FollowRepository;
import com.chalkboard.chums.repository.LikeRepository;
import com.chalkboard.chums.repository.PDFNoteRepository;
import com.chalkboard.chums.repository.TagRepository;
import com.chalkboard.chums.repository.UserRepository;

@SpringBootApplication
public class ChalkboardChumsApplication implements CommandLineRunner {

	@Autowired private UserRepository userRepository;
	@Autowired private TagRepository tagRepository;
	@Autowired private PDFNoteRepository pdfNoteRepository;
	@Autowired private LikeRepository likeRepository;
	@Autowired private FollowRepository followRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ChalkboardChumsApplication.class, args);
	}
	
	@Override
    public void run(String... args) {
        User user = User.builder()
                .username("tanaka")
                .displayName("田中")
                .email("t@example.com")
                .passwordHash("hashed-password")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        
        User user2 = User.builder()
        		.username("suzuki")
        	    .displayName("鈴木")
        	    .email("s@example.com")
        	    .passwordHash("another-password")
        	    .createdAt(LocalDateTime.now())
        	    .build();
        	userRepository.save(user2);
        
        
        

        Tag tag1 = Tag.builder().name("#数学").type(TagType.MANUAL).build();
        Tag tag2 = Tag.builder().name("#図形").type(TagType.SUBJECT).build();
        tagRepository.saveAll(Set.of(tag1, tag2));

        PDFNote note = PDFNote.builder()
                .title("今日の数学ノート")
                .filePath("s3://bucket/pdf/math1.pdf")
                .uploader(user)
                .visibility(Visibility.PUBLIC)
                .tags(Set.of(tag1, tag2))
                .build();
        pdfNoteRepository.save(note);
        
        
        Like like = Like.builder()
        	    .user(user) // さっき作ったUser
        	    .pdfNote(note) // さっき作ったPDFNote
        	    .build();
        	likeRepository.save(like);
        
        	
        Follow follow = Follow.builder()
        		.follower(user2)  // suzukiが
        		.followee(user)   // tanakaをフォロー
        	    .build();
      		followRepository.save(follow);
	}


}
