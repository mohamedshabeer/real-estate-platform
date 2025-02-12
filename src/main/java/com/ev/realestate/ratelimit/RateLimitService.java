package com.ev.realestate.ratelimit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import java.time.Duration;

public class RateLimitService {
    private final LoadingCache<String, Bucket> buckets;
    private final int capacity;
    private final Duration duration;

    public RateLimitService(int capacity, Duration duration) {
        this.capacity = capacity;
        this.duration = duration;
        this.buckets = Caffeine.newBuilder()
                .expireAfterAccess(duration.plusMinutes(1))
                .build(key -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(capacity,
                Refill.intervally(capacity, duration));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean tryConsume(String key) {
        return buckets.get(key).tryConsume(1);
    }
}
