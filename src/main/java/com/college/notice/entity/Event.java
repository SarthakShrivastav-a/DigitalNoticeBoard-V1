package com.college.notice.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Event {


    private String id;
    private String eventName;
    private String description;
    private LocalDateTime registrationDeadline;
    private LocalDateTime eventDate;
    private List<String> registeredUsers;
    private int reminderBefore; // Days before event to send a reminder

}
