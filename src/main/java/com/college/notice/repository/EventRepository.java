package com.college.notice.repository;

import com.college.notice.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByCategory(String category);
    List<Event> findByRegisteredUsersContaining(String userId);
}
