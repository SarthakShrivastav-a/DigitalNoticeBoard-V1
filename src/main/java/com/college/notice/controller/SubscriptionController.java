package com.college.notice.controller;

import com.college.notice.entity.Subscription;
import com.college.notice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PreAuthorize("hasRole('USER') or hasRole('FACULTY')")
    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribe(@RequestBody List<String> categories) {
        String userId = getCurrentUserId();
        Subscription subscription = subscriptionService.subscribe(userId, categories);
        return ResponseEntity.ok(subscription);
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('FACULTY')")
    @GetMapping("/user")
    public ResponseEntity<Optional<Subscription>> getUserSubscriptions() {
        String userId = getCurrentUserId();
        Optional<Subscription> subscription = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscription);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByCategory(@PathVariable String category) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByCategory(category);
        return ResponseEntity.ok(subscriptions);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/categories")
//    public ResponseEntity<List<Subscription>> getSubscriptionsByCategories(@RequestBody List<String> categories) {
//        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByCategories(categories);
//        return ResponseEntity.ok(subscriptions);
//    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('FACULTY')")
    @PostMapping("/unsubscribe")
    public ResponseEntity<Subscription> unsubscribe(@RequestBody List<String> categories) {
        String userId = getCurrentUserId();
        Subscription updatedSubscription = subscriptionService.unsubscribe(userId, categories);
        return ResponseEntity.ok(updatedSubscription);
    }

    private String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();  // Assuming username is the userId
        } else {
            throw new IllegalStateException("User is not authenticated");
        }
    }
}
