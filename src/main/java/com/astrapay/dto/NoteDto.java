package com.astrapay.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoteDto {
	public String id;
	public String title;
	public String notes;
	public LocalDateTime created_at;
	public LocalDateTime last_updated;
	
	public void addNote(String title, String note) {
		this.title = title;
		this.notes = note;
		this.created_at = LocalDateTime.now();
		this.last_updated = LocalDateTime.now();
		this.id = created_at.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS"));
	}
	
	public void updateNote(String title, String note) {
		this.title = title;
		this.notes = note;
		this.last_updated = LocalDateTime.now();
	}
}
