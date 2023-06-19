package com.weather.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager<T> {
    private Map<String, CacheEntry<T>> cache = new ConcurrentHashMap<>();

    public void put(String key, T value, long ttlMillis) {
        long expirationTime = System.currentTimeMillis() + ttlMillis;
        CacheEntry<T> entry = new CacheEntry<>(value, expirationTime);
        cache.put(key, entry);
    }

    public T get(String key) {
        CacheEntry<T> entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        } else {
            cache.remove(key);
            return null;
        }
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}
