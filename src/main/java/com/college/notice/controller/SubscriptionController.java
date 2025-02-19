package com.college.notice.controller;

import com.college.notice.entity.Subscription;
import com.college.notice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'ADMIN', 'CLUBLEAD')")
    @PostMapping
    public Subscription subscribe(@RequestBody List<String> categories) {
        String userId = getCurrentUserEmail();
        return subscriptionService.subscribe(userId, categories);
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'ADMIN', 'CLUBLEAD')")
    @GetMapping
    public Optional<Subscription> getUserSubscriptions() {
        String userId = getCurrentUserEmail();
        return subscriptionService.getUserSubscriptions(userId);
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'ADMIN', 'CLUBLEAD')")
    @DeleteMapping
    public Subscription unsubscribe(@RequestBody List<String> categories) {
        String userId = getCurrentUserEmail();
        return subscriptionService.unsubscribe(userId, categories);
    }
    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
