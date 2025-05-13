package com.webrise.controller;

import com.webrise.dto.SubscriptionDto;
import com.webrise.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDto> add(@PathVariable Long userId, @RequestBody @Valid SubscriptionDto dto) {
        logger.info("Received request to add subscription for userId: {} with service: {}", userId, dto.getServiceName());
        SubscriptionDto created = subscriptionService.addSubscription(userId, dto);
        logger.info("Subscription created with ID: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> list(@PathVariable Long userId) {
        logger.info("Received request to list subscriptions for userId: {}", userId);
        List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptions(userId);
        logger.info("Returned {} subscriptions for userId: {}", subscriptions.size(), userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{subId}")
    public ResponseEntity<Void> delete(@PathVariable Long subId) {
        logger.info("Received request to delete subscription with ID: {}", subId);
        subscriptionService.deleteSubscription(subId);
        logger.info("Subscription with ID {} deleted", subId);
        return ResponseEntity.noContent().build();
    }
}

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
class TopSubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(TopSubscriptionController.class);

    private final SubscriptionService subscriptionService;

    @GetMapping("/top")
    public List<String> top() {
        logger.info("Received request to fetch top subscriptions");
        List<String> topSubscriptions = subscriptionService.getTopSubscriptions();
        logger.info("Top subscriptions: {}", topSubscriptions);
        return topSubscriptions;
    }
}
