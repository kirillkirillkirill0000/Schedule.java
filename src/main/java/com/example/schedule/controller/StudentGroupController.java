package com.example.schedule.controller;

import com.example.schedule.model.StudentGroup;
import com.example.schedule.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-groups")
public class StudentGroupController {
    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<StudentGroup>> createStudentGroupsBulk(@RequestBody List<StudentGroup> studentGroups) {
        List<StudentGroup> savedGroups = studentGroupService.saveAll(studentGroups);
        return ResponseEntity.ok(savedGroups);
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
    public ResponseEntity<StudentGroup> updateStudentGroup(
            @PathVariable Long id,
            @RequestBody StudentGroup studentGroupDetails) {
        if (id == null || !id.equals(studentGroupDetails.getId())) {
            return ResponseEntity.badRequest().build();
        }
        StudentGroup updatedGroup = studentGroupService.update(id, studentGroupDetails);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentGroup(@PathVariable Long id) {
        studentGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}