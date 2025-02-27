package com.example.schedule.controller;

import com.example.schedule.model.StudentGroup;
import com.example.schedule.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Контроллер для управления группами студентов
@RestController
@RequestMapping("/student-groups")
public class StudentGroupController {

    // Сервис для работы с группами студентов
    private final StudentGroupService studentGroupService;

    // Конструктор внедрения зависимости StudentGroupService
    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    // создания новой группы студентов
    @PostMapping
    public ResponseEntity<StudentGroup> createStudentGroup(@RequestBody StudentGroup studentGroup) {
        // Сохранение группы и возвращение её в ответе
        StudentGroup savedGroup = studentGroupService.save(studentGroup);
        return ResponseEntity.ok(savedGroup);
    }

    // получения списка всех групп студентов
    @GetMapping
    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupService.findAll();
    }

    // получения группы студентов по ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentGroup> getStudentGroupById(@PathVariable Long id) {
        // Возвращение группы, если найдена, иначе 404
        return studentGroupService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  обновления существующей группы студентов
    @PutMapping("/{id}")
    public ResponseEntity<StudentGroup> updateStudentGroup(@PathVariable Long id, @RequestBody StudentGroup studentGroupDetails) {
        return studentGroupService.findById(id)
                .map(studentGroup -> {
                    // Обновление полей группы студентов
                    studentGroup.setSpecialityName(studentGroupDetails.getSpecialityName());
                    studentGroup.setName(studentGroupDetails.getName());
                    // Сохранение обновленной группы и возвращение её в ответе
                    StudentGroup updatedGroup = studentGroupService.save(studentGroup);
                    return ResponseEntity.ok(updatedGroup);
                })
                .orElse(ResponseEntity.notFound().build()); // Возвращение 404, если не найдено
    }

    // удаления группы студентов по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentGroup(@PathVariable Long id) {
        studentGroupService.delete(id); // Удаление группы студентов
        return ResponseEntity.noContent().build(); // Возвращение 204 No Content
    }
}