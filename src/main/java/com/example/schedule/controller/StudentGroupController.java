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


    @Autowired// Конструктор внедрения зависимости StudentGroupService
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }


    @PostMapping// создания новой группы студентов
    public ResponseEntity<StudentGroup> createStudentGroup(@RequestBody StudentGroup studentGroup) {
        // Сохранение группы и возвращение её в ответе
        StudentGroup savedGroup = studentGroupService.save(studentGroup);
        return ResponseEntity.ok(savedGroup);
    }


    @GetMapping// получения списка всех групп студентов
    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupService.findAll();
    }


    @GetMapping("/{id}") // получения группы студентов по ID
    public ResponseEntity<StudentGroup> getStudentGroupById(@PathVariable Long id) {
        // Возвращение группы, если найдена, иначе 404
        return studentGroupService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentGroup> updateStudentGroup(@RequestBody StudentGroup studentGroupDetails) {
        if (studentGroupDetails.getId() == null) {
            return ResponseEntity.badRequest().build(); // Возвращаем 400, если ID не указан
        }

        StudentGroup updatedGroup = studentGroupService.update(studentGroupDetails.getId(), studentGroupDetails);
        return ResponseEntity.ok(updatedGroup); // Возвращаем обновленную группу
    }


    @DeleteMapping("/{id}")// удаления группы студентов по ID
    public ResponseEntity<Void> deleteStudentGroup(@PathVariable Long id) {
        studentGroupService.delete(id); // Удаление группы студентов
        return ResponseEntity.noContent().build(); // Возвращение 204 No Content
    }
}