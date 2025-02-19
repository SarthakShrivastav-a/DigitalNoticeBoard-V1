package com.college.notice.repository;

import com.college.notice.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    Optional<Subscription> findByUserId(String userId);

    List<Subscription> findByCategories(String category);
}
