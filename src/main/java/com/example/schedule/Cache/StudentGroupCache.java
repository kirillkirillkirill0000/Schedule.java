package com.example.schedule.Cache;

import com.example.schedule.model.StudentGroup;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentGroupCache {

    private final Map<Long, StudentGroup> idCache = new HashMap<>();

    private final Map<String, List<StudentGroup>> customKeyCache = new HashMap<>();

    public StudentGroup get(Long id) {
        return idCache.get(id);
    }

    public void put(Long id, StudentGroup group) {
        idCache.put(id, group);
    }

    public void invalidate(Long id) {
        idCache.remove(id);
    }

    public List<StudentGroup> getAll() {
        return List.copyOf(idCache.values());
    }

    public void putAll(List<StudentGroup> groups) {
        groups.forEach(g -> idCache.put(g.getId(), g));
    }

    public List<StudentGroup> getByCustomKey(String key) {
        return customKeyCache.get(key);
    }

    public void putByCustomKey(String key, List<StudentGroup> groups) {
        customKeyCache.put(key, groups);
    }

    public void clear() {
        idCache.clear();
        customKeyCache.clear();
    }

    public boolean contains(Long id) {
        return idCache.containsKey(id);
    }
}