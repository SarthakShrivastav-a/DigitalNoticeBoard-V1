package com.college.notice.service;

import com.college.notice.entity.*;
import com.college.notice.exception.*;
import com.college.notice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private NotificationService notificationService;

    @Cacheable("events")
    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'ADMIN')")
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findByIsDeletedFalse(pageable);
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'ADMIN')")
    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .filter(event -> !event.isDeleted())
                .orElseThrow(() -> new EventNotFoundException("Event not found: " + id));
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @Transactional
    public Event createEvent(Event event) {
        validateEvent(event);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        event.setCreatedBy(createdBy);
        event.setCreatedAt(LocalDateTime.now());
        event.setRegistrations(new ArrayList<>());
        event.setStatus(Event.EventStatus.UPCOMING);
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteEvent(String id) {
        Event event = getEventById(id);
        event.setDeleted(true);
        eventRepository.save(event);
        notificationService.notifyRegisteredUsers(event, "Event Cancelled");
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY')")
    @Transactional
    public Event registerForEvent(String eventId, String userEmail) { //, Map<String, String> customFields
        Event event = getEventById(eventId);
        validateRegistration(event, userEmail);

        Registration registration = new Registration();
        registration.setUserEmail(userEmail);
        registration.setRegistrationDate(LocalDateTime.now());
//        registration.setCustomFields(customFields);

        if (event.getRegistrations().size() >= event.getCapacity()) {
            registration.setStatus(Registration.RegistrationStatus.PENDING);
            event.getWaitlist().add(userEmail);
            notificationService.notifyUserWaitlisted(userEmail, event);
        } else {
            registration.setStatus(Registration.RegistrationStatus.CONFIRMED);
            notificationService.notifyUserConfirmed(userEmail, event);
        }

        event.getRegistrations().add(registration);
        return eventRepository.save(event);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @Transactional
    public Event updateEventStatus(String eventId, Event.EventStatus status) {
        Event event = getEventById(eventId);
        event.setStatus(status);
        event.setUpdatedAt(LocalDateTime.now());
        notificationService.notifyRegisteredUsers(event, "Event Status Updated");
        return eventRepository.save(event);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @Transactional
    public Event markAttendance(String eventId, String userEmail, boolean attended) {
        Event event = getEventById(eventId);
        event.getRegistrations().stream()
                .filter(reg -> reg.getUserEmail().equals(userEmail))
                .findFirst()
                .ifPresent(reg -> reg.setAttended(attended));
        return eventRepository.save(event);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    public List<Registration> getEventAttendance(String eventId) {
        Event event = getEventById(eventId);
        return event.getRegistrations();
    }

    private void validateEvent(Event event) {
        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new InvalidEventException("Event date cannot be in the past");
        }
        if (event.getCapacity() <= 0) {
            throw new InvalidEventException("Event capacity must be positive");
        }

    }

    private void validateRegistration(Event event, String userEmail) {
        if (event.getRegistrationDeadline().isBefore(LocalDateTime.now())) {
            throw new RegistrationClosedException("Registration deadline has passed");
        }

        if (event.getRegistrations().stream().anyMatch(reg -> reg.getUserEmail().equals(userEmail))) {
            throw new DuplicateRegistrationException("Already registered for this event");
        }

        boolean overlappingEvent = eventRepository.findByRegistrations_UserEmail(userEmail)
                .stream()
                .anyMatch(e -> e.getEventDate().isEqual(event.getEventDate()));

        if (overlappingEvent) {
            throw new InvalidRegistrationException("Cannot register for two events at the same time.");
        }
    }
}
