package com.project.library.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Rate limiter to prevent abuse. 5 requests is limit in a minute.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Service
public class RateLimiterService {

    private final Bucket globalBucket;

    public RateLimiterService() {
        this.globalBucket = Bucket4j.builder()
                .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1))))
                .build();
    }

    public boolean tryConsume() {
        return globalBucket.tryConsume(1);
    }
}