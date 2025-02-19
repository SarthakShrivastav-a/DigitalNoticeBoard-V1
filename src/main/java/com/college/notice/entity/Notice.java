package com.college.notice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notices")
public class Notice {

    @Id
    private String id;
    private String title;
    private String content;
    private String category; // enum with EXAMS, CLUBS, PLACEMENTS, GENERAL,EVENTS
    private String createdBy;
    private LocalDateTime createdAt;
    private String visibility; // we can define an enum for PUBLIC & FACULTY in the frontend ig
    private String editedBy;
    public Notice() {
        this.createdAt = LocalDateTime.now();
    }
    public Notice(String title, String content,String category, String createdBy, String visibility) {
        this.title = title;
        this.content = content;
        this.category=category;
        this.createdBy = createdBy;
        this.visibility = visibility;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public String getCategory() {
        return category;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
