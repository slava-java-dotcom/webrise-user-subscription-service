package com.webrise.repository;

import com.webrise.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    @Query("SELECT s.serviceName FROM Subscription s GROUP BY s.serviceName ORDER BY COUNT(s.id) DESC LIMIT 3")
    List<String> findTop3PopularSubscriptions();
}