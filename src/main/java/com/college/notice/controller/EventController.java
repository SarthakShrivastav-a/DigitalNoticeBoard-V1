package com.college.notice.controller;

import com.college.notice.entity.Event;
import com.college.notice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class EventController {


    @Autowired
    EventService eventService;


//    @PreAuthorize("hasRole('STUDENT')")
//    @PostMapping("/events/{eventId}/register")
//    public ResponseEntity<Event> registerForEvent(@PathVariable String eventId) {
//        String userEmail = getCurrentUserEmail(); // Fetch user email in controller
//        Event event = eventService.registerForEvent(eventId, userEmail);
//        return ResponseEntity.ok(event);
//    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // Assuming username is email
        }
        throw new IllegalStateException("User not authenticated.");
    }

}
