package com.college.notice.controller;

import com.college.notice.entity.Event;
import com.college.notice.entity.Registration;
import com.college.notice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY')")
    @PostMapping("/{eventId}/register")
    public ResponseEntity<Event> registerForEvent(@PathVariable String eventId,
                                                  @RequestParam String userEmail,
                                                  @RequestBody Map<String, String> customFields) {
        return ResponseEntity.ok(eventService.registerForEvent(eventId, userEmail, customFields));
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @PutMapping("/{eventId}/status")
    public ResponseEntity<Event> updateEventStatus(@PathVariable String eventId,
                                                   @RequestParam Event.EventStatus status) {
        return ResponseEntity.ok(eventService.updateEventStatus(eventId, status));
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @PutMapping("/{eventId}/attendance")
    public ResponseEntity<Event> markAttendance(@PathVariable String eventId,
                                                @RequestParam String userEmail,
                                                @RequestParam boolean attended) {
        return ResponseEntity.ok(eventService.markAttendance(eventId, userEmail, attended));
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @GetMapping("/{eventId}/attendance")
    public ResponseEntity<List<Registration>> getEventAttendance(@PathVariable String eventId) {
        return ResponseEntity.ok(eventService.getEventAttendance(eventId));
    }
}
