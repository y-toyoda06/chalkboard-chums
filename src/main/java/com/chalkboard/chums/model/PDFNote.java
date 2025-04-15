package com.chalkboard.chums.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pdf_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PDFNote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@Column(nullable = false)
	private String filePath;
	
	@ManyToOne(fetch = FetchType.LAZY) //複数のpdfが一人のuserに属す
	@JoinColumn(name = "uploader_id", nullable = false)
	private User uploader;
	
	@Enumerated(EnumType.STRING)//EnumをDBに保存するとき文字列として保存する
	@Column(nullable = false)
	private Visibility visibility;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	
	//pdfとtagの多対多の処理（中間テーブル）
	@ManyToMany
	@JoinTable(
			name = "pdf_note_tags",
			joinColumns = @JoinColumn(name = "pdf_note_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
			)
	private Set<Tag> tags;  //Setで重複を避ける（リスト）
	

}
