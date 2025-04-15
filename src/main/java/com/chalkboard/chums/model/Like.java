
package com.chalkboard.chums.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "likes", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "pdf_note_id"})
}) //likeを押せるのは一回だけにする．（user_id と pdf_note_id の組み合わせが重複しないようにする）
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 誰が
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    //どのノートに
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_note_id", nullable = false)
    private PDFNote pdfNote;
    
    //いつ
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
