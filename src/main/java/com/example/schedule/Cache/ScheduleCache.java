package com.example.schedule.Cache;

import com.example.schedule.model.Schedule;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleCache {
    private final Map<Long, Schedule> cache = new HashMap<>();

    public Schedule get(Long id) {
        return cache.get(id);
    }

    public void put(Long id, Schedule schedule) {
        cache.put(id, schedule);
    }

    public void invalidate(Long id) {
        cache.remove(id);
    }

    public void clear() {
        cache.clear();
    }

    public List<Schedule> getAll() {
        return List.copyOf(cache.values());
    }
}
