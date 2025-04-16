package com.example.schedule.service;

import com.example.schedule.Cache.StudentGroupCache;
import com.example.schedule.dao.StudentGroupRepository;
import com.example.schedule.model.StudentGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentGroupServiceTest {
    @Mock
    private StudentGroupRepository studentGroupRepository;
    @Mock
    private StudentGroupCache studentGroupCache;
    @InjectMocks
    private StudentGroupService studentGroupService;
    private StudentGroup studentGroup;

    @BeforeEach
    void setUp() {
        studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setSpecialityName("Компьютерная инженерия");
        studentGroup.setName("334702");
    }

    @Test
    void saveAll_ValidGroups_ReturnsSavedGroups() {
        StudentGroup group2 = new StudentGroup();
        group2.setId(2L);
        group2.setSpecialityName("Системы и сети инфокоммуникаций");
        group2.setName("333702");
        List<StudentGroup> inputGroups = Arrays.asList(studentGroup, group2);
        List<StudentGroup> savedGroups = Arrays.asList(studentGroup, group2);
        when(studentGroupRepository.saveAll(anyList())).thenReturn(savedGroups);
        List<StudentGroup> result = studentGroupService.saveAll(inputGroups);
        assertEquals(2, result.size());
        assertEquals("334702", result.get(0).getName());
        assertEquals("333702", result.get(1).getName());
        verify(studentGroupRepository).saveAll(inputGroups);
        verify(studentGroupCache, times(2)).put(anyLong(), any(StudentGroup.class));
    }

    @Test
    void saveAll_GroupsWithNullFields_FiltersOutInvalid() {
        StudentGroup invalidGroup = new StudentGroup();
        invalidGroup.setId(2L);
        invalidGroup.setSpecialityName(null);
        invalidGroup.setName("333702");
        List<StudentGroup> inputGroups = Arrays.asList(studentGroup, invalidGroup);
        List<StudentGroup> savedGroups = Collections.singletonList(studentGroup);
        when(studentGroupRepository.saveAll(anyList())).thenReturn(savedGroups);
        List<StudentGroup> result = studentGroupService.saveAll(inputGroups);
        assertEquals(1, result.size());
        assertEquals("334702", result.getFirst().getName());
        verify(studentGroupRepository).saveAll(Collections.singletonList(studentGroup));
        verify(studentGroupCache).put(1L, studentGroup);
    }

    @Test
    void findAll_CacheHit_ReturnsCachedGroups() {
        List<StudentGroup> cachedGroups = Collections.singletonList(studentGroup);
        when(studentGroupCache.getAll()).thenReturn(cachedGroups);
        List<StudentGroup> result = studentGroupService.findAll();
        assertEquals(1, result.size());
        assertEquals("334702", result.getFirst().getName());
        verify(studentGroupCache).getAll();
        verify(studentGroupRepository, never()).findAll();
    }

    @Test
    void findAll_CacheMiss_ReturnsFromRepository() {
        List<StudentGroup> groups = Collections.singletonList(studentGroup);
        when(studentGroupCache.getAll()).thenReturn(Collections.emptyList());
        when(studentGroupRepository.findAll()).thenReturn(groups);
        List<StudentGroup> result = studentGroupService.findAll();
        assertEquals(1, result.size());
        assertEquals("334702", result.getFirst().getName());
        verify(studentGroupCache).getAll();
        verify(studentGroupRepository).findAll();
        verify(studentGroupCache).putAll(groups);
    }

    @Test
    void findById_CacheHit_ReturnsCachedGroup() {
        when(studentGroupCache.get(1L)).thenReturn(studentGroup);
        Optional<StudentGroup> result = studentGroupService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("334702", result.get().getName());
        verify(studentGroupCache).get(1L);
        verify(studentGroupRepository, never()).findById(anyLong());
    }

    @Test
    void findById_CacheMiss_ReturnsFromRepository() {
        when(studentGroupCache.get(1L)).thenReturn(null);
        when(studentGroupRepository.findById(1L)).thenReturn(Optional.of(studentGroup));
        Optional<StudentGroup> result = studentGroupService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("334702", result.get().getName());
        verify(studentGroupCache).get(1L);
        verify(studentGroupRepository).findById(1L);
        verify(studentGroupCache).put(1L, studentGroup);
    }

    @Test
    void save_ValidGroup_ReturnsSavedGroup() {
        when(studentGroupRepository.save(studentGroup)).thenReturn(studentGroup);
        StudentGroup result = studentGroupService.save(studentGroup);
        assertNotNull(result);
        assertEquals("334702", result.getName());
        verify(studentGroupRepository).save(studentGroup);
        verify(studentGroupCache).put(1L, studentGroup);
    }

    @Test
    void update_GroupExists_ReturnsUpdatedGroup() {
        StudentGroup updatedDetails = new StudentGroup();
        updatedDetails.setSpecialityName("Радиосистемы и радиотехнологии");
        updatedDetails.setName("334201");
        when(studentGroupRepository.findById(1L)).thenReturn(Optional.of(studentGroup));
        when(studentGroupRepository.save(any(StudentGroup.class))).thenReturn(studentGroup);
        StudentGroup result = studentGroupService.update(1L, updatedDetails);
        assertEquals("334201", result.getName());
        assertEquals("Радиосистемы и радиотехнологии", result.getSpecialityName());
        verify(studentGroupRepository).findById(1L);
        verify(studentGroupRepository).save(studentGroup);
        verify(studentGroupCache).put(1L, studentGroup);
    }

    @Test
    void update_GroupNotFound_ThrowsException() {
        StudentGroup updatedDetails = new StudentGroup();
        when(studentGroupRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentGroupService.update(1L, updatedDetails));
        verify(studentGroupRepository).findById(1L);
        verify(studentGroupRepository, never()).save(any());
    }

    @Test
    void delete_GroupExists_DeletesSuccessfully() {
        studentGroupService.delete(1L);
        verify(studentGroupRepository).deleteById(1L);
        verify(studentGroupCache).invalidate(1L);
    }
}