package com.college.notice.service;

import com.college.notice.entity.Event;
import com.college.notice.exception.EventNotFoundException;
import com.college.notice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public Event registerForEvent(String eventId, String userEmail) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found: " + eventId));


        if (event.getRegistrationDeadline().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Registration deadline has passed for this event.");
        }
        if (event.getRegisteredUsers().contains(userEmail)) {
            throw new IllegalStateException("You already registered for this event.");
        }
        event.getRegisteredUsers().add(userEmail);
        return eventRepository.save(event);
    }

}