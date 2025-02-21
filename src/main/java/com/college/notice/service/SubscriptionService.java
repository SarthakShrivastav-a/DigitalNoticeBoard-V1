package com.college.notice.service;

import com.college.notice.entity.Subscription;
import com.college.notice.exception.SubscriptionNotFoundException;
import com.college.notice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public Subscription subscribe(String userId, List<String> categories) {
        validateCategories(categories);

        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(userId);

        if (existingSubscription.isPresent()) {
            Subscription subscription = existingSubscription.get();
            Set<String> uniqueCategories = new HashSet<>(subscription.getCategories());
            uniqueCategories.addAll(categories);
            subscription.setCategories(new ArrayList<>(uniqueCategories));
            subscription.setLastUpdated(LocalDateTime.now());
            return subscriptionRepository.save(subscription);
        }

        Subscription newSubscription = new Subscription(userId, new ArrayList<>(new HashSet<>(categories)));
        return subscriptionRepository.save(newSubscription);
    }

    public Optional<Subscription> getUserSubscriptions(String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public List<Subscription> getSubscriptionsByCategory(String category) {
        return subscriptionRepository.findByCategories(category);
    }

    @Transactional
    public Subscription unsubscribe(String userId, List<String> categories) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new SubscriptionNotFoundException("User not subscribed: " + userId));

        subscription.getCategories().removeAll(categories);
        subscription.setLastUpdated(LocalDateTime.now());

        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void deleteSubscription(String userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new SubscriptionNotFoundException("User not subscribed: " + userId));

        subscriptionRepository.delete(subscription);
    }

    private void validateCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be empty");
        }

        Set<String> validCategories = Set.of("ACADEMIC", "SPORTS", "CULTURAL", "PLACEMENT", "GENERAL", "EVENTS");
        if (!validCategories.containsAll(categories)) {
            throw new IllegalArgumentException("Invalid categories provided");
        }
    }
}