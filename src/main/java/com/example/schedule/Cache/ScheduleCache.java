// ScheduleCache.java
package com.example.schedule.Cache;

import com.example.schedule.model.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ScheduleCache {
    private static final long TTL_MINUTES = 30;

    private final Map<Long, CacheEntry> idCache = new ConcurrentHashMap<>();
    private final Map<String, List<Schedule>> customKeyCache = new ConcurrentHashMap<>();

    private static class CacheEntry {
        Schedule schedule;
        LocalDateTime expirationTime;

        CacheEntry(Schedule schedule) {
            this.schedule = schedule;
            this.expirationTime = LocalDateTime.now().plusMinutes(TTL_MINUTES);
        }

        boolean isExpired() {
            return LocalDateTime.now().isAfter(expirationTime);
        }
    }

    public Schedule get(Long id) {
        CacheEntry entry = idCache.get(id);
        if (entry == null || entry.isExpired()) {
            if (entry != null) {
                idCache.remove(id);
            }
            return null;
        }
        return entry.schedule;
    }

    public void put(Long id, Schedule schedule) {
        idCache.put(id, new CacheEntry(schedule));
    }

    public void invalidate(Long id) {
        idCache.remove(id);
    }

    public void clear() {
        idCache.clear();
        customKeyCache.clear();
    }

    public List<Schedule> getAll() {
        return idCache.values().stream()
                .filter(entry -> !entry.isExpired())
                .map(entry -> entry.schedule)
                .collect(Collectors.toList());
    }

    public void putAll(List<Schedule> schedules) {
        schedules.forEach(s -> idCache.put(s.getId(), new CacheEntry(s)));
    }

    public List<Schedule> getByCustomKey(String key) {
        return customKeyCache.get(key);
    }

    public void putByCustomKey(String key, List<Schedule> schedules) {
        customKeyCache.put(key, schedules);
    }
}