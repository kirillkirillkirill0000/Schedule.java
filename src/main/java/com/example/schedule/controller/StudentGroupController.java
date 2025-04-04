package com.example.schedule.controller;

import com.example.schedule.model.StudentGroup;
import com.example.schedule.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/student-groups")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;


    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }


    @PostMapping
    public ResponseEntity<StudentGroup> createStudentGroup(@RequestBody StudentGroup studentGroup) {
        StudentGroup savedGroup = studentGroupService.save(studentGroup);
        return ResponseEntity.ok(savedGroup);
    }


    @GetMapping
    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentGroup> getStudentGroupById(@PathVariable Long id) {

        return studentGroupService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentGroup> updateStudentGroup(@RequestBody StudentGroup studentGroupDetails) {
        if (studentGroupDetails.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        StudentGroup updatedGroup = studentGroupService.update(studentGroupDetails.getId(), studentGroupDetails);
        return ResponseEntity.ok(updatedGroup);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentGroup(@PathVariable Long id) {
        studentGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}