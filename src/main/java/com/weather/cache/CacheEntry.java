package com.weather.cache;

public class CacheEntry<T> {
    private T value;
    private long expirationTime;

    public CacheEntry(T value, long expirationTime) {
        this.value = value;
        this.expirationTime = expirationTime;
    }

    public T getValue() {
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}
