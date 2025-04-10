package com.example.schedule.Cache;

import com.example.schedule.model.Schedule;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleCache {
    private final Map<Long, Schedule> idCache = new HashMap<>();

    private final Map<String, List<Schedule>> customKeyCache = new HashMap<>();

    public Schedule get(Long id) {
        return idCache.get(id);
    }

    public void put(Long id, Schedule schedule) {
        idCache.put(id, schedule);
    }

    public void invalidate(Long id) {
        idCache.remove(id);
    }

    public List<Schedule> getAll() {
        return List.copyOf(idCache.values());
    }

    public void putAll(List<Schedule> schedules) {
        schedules.forEach(s -> idCache.put(s.getId(), s));
    }

    public List<Schedule> getByCustomKey(String key) {
        return customKeyCache.get(key);
    }

    public void putByCustomKey(String key, List<Schedule> schedules) {
        customKeyCache.put(key, schedules);
    }
}