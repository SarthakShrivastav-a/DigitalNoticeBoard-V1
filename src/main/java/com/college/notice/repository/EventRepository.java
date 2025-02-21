package com.college.notice.repository;

import com.college.notice.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    Page<Event> findByIsDeletedFalse(Pageable pageable);
    List<Event> findByStatusAndIsDeletedFalse(Event.EventStatus status);
    List<Event> findByDepartmentAndIsDeletedFalse(String department);
    List<Event> findByEventDateBetweenAndIsDeletedFalse(LocalDateTime start, LocalDateTime end);
    List<Event> findByTagsContainingAndIsDeletedFalse(String tag);
    List<Event> findByVisibilityAndIsDeletedFalse(Event.EventVisibility visibility);
    List<Event> findByRegistrations_UserEmail(String UserEmail);
}