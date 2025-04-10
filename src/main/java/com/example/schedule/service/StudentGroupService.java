package com.example.schedule.service;

import com.example.schedule.Cache.StudentGroupCache;
import com.example.schedule.dao.StudentGroupRepository;
import com.example.schedule.model.StudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    private final StudentGroupCache studentGroupCache;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository,
                               StudentGroupCache studentGroupCache) {
        this.studentGroupRepository = studentGroupRepository;
        this.studentGroupCache = studentGroupCache;
    }

    public List<StudentGroup> findAll() {
        List<StudentGroup> cachedGroups = studentGroupCache.getAll();
        if (!cachedGroups.isEmpty()) {
            return cachedGroups;
        }
        List<StudentGroup> groups = studentGroupRepository.findAll();
        studentGroupCache.putAll(groups);
        return groups;
    }

    public Optional<StudentGroup> findById(Long id) {
        StudentGroup cachedGroup = studentGroupCache.get(id);
        if (cachedGroup != null) {
            return Optional.of(cachedGroup);
        }
        Optional<StudentGroup> group = studentGroupRepository.findById(id);
        group.ifPresent(g -> studentGroupCache.put(g.getId(), g));
        return group;
    }

    @Transactional
    public StudentGroup save(StudentGroup studentGroup) {
        StudentGroup savedGroup = studentGroupRepository.save(studentGroup);
        studentGroupCache.put(savedGroup.getId(), savedGroup);
        return savedGroup;
    }

    @Transactional
    public StudentGroup update(Long id, StudentGroup studentGroupDetails) {
        StudentGroup studentGroup = studentGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentGroup not found with id " + id));
        studentGroup.setSpecialityName(studentGroupDetails.getSpecialityName());
        studentGroup.setName(studentGroupDetails.getName());
        StudentGroup updatedGroup = studentGroupRepository.save(studentGroup);
        studentGroupCache.put(id, updatedGroup);
        return updatedGroup;
    }

    @Transactional
    public void delete(Long id) {
        studentGroupRepository.deleteById(id);
        studentGroupCache.invalidate(id);
    }
}