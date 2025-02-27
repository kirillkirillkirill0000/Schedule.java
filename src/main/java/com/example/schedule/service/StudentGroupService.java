package com.example.schedule.service;

import com.example.schedule.dao.StudentGroupRepository;
import com.example.schedule.model.StudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Сервис для работы с группами студентов
@Service
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    // Конструктор для внедрения зависимости
    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    //  для получения всех групп студентов
    public List<StudentGroup> findAll() {
        return studentGroupRepository.findAll();
    }

    // для поиска группы студентов по идентификатору
    public Optional<StudentGroup> findById(Long id) {
        return studentGroupRepository.findById(id);
    }

    // для сохранения новой группы студентов
    public StudentGroup save(StudentGroup studentGroup) {
        return studentGroupRepository.save(studentGroup);
    }

    // для обновления существующей группы студентов
    public StudentGroup update(Long id, StudentGroup studentGroupDetails) {
        StudentGroup studentGroup = studentGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentGroup not found with id " + id));
        // Обновление полей
        studentGroup.setSpecialityName(studentGroupDetails.getSpecialityName());
        studentGroup.setName(studentGroupDetails.getName());
        return studentGroupRepository.save(studentGroup); // Сохранение обновленной группы
    }

    //  для удаления группы студентов по идентификатору
    public void delete(Long id) {
        studentGroupRepository.deleteById(id);
    }
}