package com.college.notice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime eventDate;
    private LocalDateTime registrationDeadline;
    private List<String> registeredUsers;
    private boolean isCompleted;

    public Event() {}

    public Event(String title, String description, String category, LocalDateTime eventDate, LocalDateTime registrationDeadline) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.eventDate = eventDate;
        this.registrationDeadline = registrationDeadline;
        this.registeredUsers = new ArrayList<>();
        this.isCompleted = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }

    public LocalDateTime getRegistrationDeadline() { return registrationDeadline; }
    public void setRegistrationDeadline(LocalDateTime registrationDeadline) { this.registrationDeadline = registrationDeadline; }

    public List<String> getRegisteredUsers() { return registeredUsers; }
    public void setRegisteredUsers(List<String> registeredUsers) { this.registeredUsers = registeredUsers; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
