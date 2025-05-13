package com.webrise.service;

import com.webrise.dto.SubscriptionDto;
import com.webrise.entity.Subscription;
import com.webrise.entity.User;
import com.webrise.exception.NotFoundException;
import com.webrise.repository.SubscriptionRepository;
import com.webrise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionDto addSubscription(Long userId, SubscriptionDto dto) {
        logger.info("Adding subscription for userId: {} with service: {}", userId, dto.getServiceName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found", userId);
                    return new NotFoundException("User not found");
                });

        Subscription sub = Subscription.builder()
                .serviceName(dto.getServiceName())
                .user(user)
                .build();

        Subscription saved = subscriptionRepository.save(sub);
        logger.info("Subscription saved with ID: {}", saved.getId());

        return toDto(saved);
    }

    public List<SubscriptionDto> getSubscriptions(Long userId) {
        logger.info("Retrieving subscriptions for userId: {}", userId);
        List<SubscriptionDto> subscriptions = subscriptionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        logger.info("Found {} subscriptions for userId: {}", subscriptions.size(), userId);
        return subscriptions;
    }

    public void deleteSubscription(Long subId) {
        logger.info("Deleting subscription with ID: {}", subId);
        subscriptionRepository.deleteById(subId);
        logger.info("Subscription with ID {} deleted", subId);
    }

    public List<String> getTopSubscriptions() {
        logger.info("Fetching top 3 popular subscriptions");
        List<String> topSubscriptions = subscriptionRepository.findTop3PopularSubscriptions();
        logger.info("Top subscriptions retrieved: {}", topSubscriptions);
        return topSubscriptions;
    }

    private SubscriptionDto toDto(Subscription s) {
        return SubscriptionDto.builder()
                .id(s.getId())
                .serviceName(s.getServiceName())
                .build();
    }
}
