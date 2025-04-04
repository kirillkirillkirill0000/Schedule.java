package com.example.schedule.service;

import com.example.schedule.dao.StudentGroupRepository;
import com.example.schedule.model.StudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public List<StudentGroup> findAll() {
        return studentGroupRepository.findAll();
    }

    public Optional<StudentGroup> findById(Long id) {
        return studentGroupRepository.findById(id);
    }

    public StudentGroup save(StudentGroup studentGroup) {
        return studentGroupRepository.save(studentGroup);
    }

    public StudentGroup update(Long id, StudentGroup studentGroupDetails) {
        StudentGroup studentGroup = studentGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentGroup not found with id " + id));
        studentGroup.setSpecialityName(studentGroupDetails.getSpecialityName());
        studentGroup.setName(studentGroupDetails.getName());
        return studentGroupRepository.save(studentGroup);
    }

    public void delete(Long id) {
        studentGroupRepository.deleteById(id);
    }
}