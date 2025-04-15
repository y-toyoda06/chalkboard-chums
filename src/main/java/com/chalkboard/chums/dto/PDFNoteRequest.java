package com.chalkboard.chums.dto;

import java.util.List;

import com.chalkboard.chums.model.Visibility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDFNoteRequest {
	
	@NotBlank(message = "タイトルは必須です")
	private String title;
	
	@NotBlank(message = "ファイルパスは必須です")
	private String filePath;
	
	@NotNull(message = "アップロードユーザーIDは必須です")
	private Long uploaderId;
	
	@NotNull(message = "タグIDリストは必須です")
	@Size(min = 1, message = "タグを1つ以上指定してください")
	private List<Long> tagIds;

	
	@NotNull(message = "公開範囲は必須です")
	private Visibility visibility;

}
