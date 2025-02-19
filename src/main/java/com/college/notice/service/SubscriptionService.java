package com.college.notice.service;

import com.college.notice.entity.Subscription;
import com.college.notice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription subscribe(String userId, List<String> categories) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(userId);

        if (existingSubscription.isPresent()) {
            Subscription subscription = existingSubscription.get();
            subscription.getCategories().addAll(categories);
            return subscriptionRepository.save(subscription);
        }

        Subscription newSubscription = new Subscription(userId, categories);
        return subscriptionRepository.save(newSubscription);
    }

    public Optional<Subscription> getUserSubscriptions(String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public Subscription unsubscribe(String userId, List<String> categories) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(userId);

        if (existingSubscription.isPresent()) {
            Subscription subscription = existingSubscription.get();
            subscription.getCategories().removeAll(categories);
            return subscriptionRepository.save(subscription);
        }

        throw new RuntimeException("User not subscribed to any category.");
    }
}
