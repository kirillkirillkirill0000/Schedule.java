package com.example.schedule.service;

import com.example.schedule.Cache.StudentGroupCache;
import com.example.schedule.dao.StudentGroupRepository;
import com.example.schedule.model.StudentGroup;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentGroupServiceTest {
    @Mock
    private StudentGroupRepository studentGroupRepository;

    @Mock
    private StudentGroupCache studentGroupCache;

    @InjectMocks
    private StudentGroupService studentGroupService;

    @Test
    void saveAllWithValidGroupsReturnsSavedGroups() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        StudentGroup group2 = mock(StudentGroup.class);
        when(studentGroup.getId()).thenReturn(1L);
        when(studentGroup.getName()).thenReturn("334702");
        when(studentGroup.getSpecialityName()).thenReturn("Компьютерная инженерия");
        when(group2.getId()).thenReturn(2L);
        when(group2.getName()).thenReturn("333702");
        when(group2.getSpecialityName()).thenReturn("Системы и сети инфокоммуникаций");

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
    void saveAllWithNullFieldsFiltersOutInvalidGroups() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        StudentGroup invalidGroup = mock(StudentGroup.class);
        when(studentGroup.getId()).thenReturn(1L);
        when(studentGroup.getName()).thenReturn("334702");
        when(invalidGroup.getId()).thenReturn(2L);
        when(invalidGroup.getSpecialityName()).thenReturn(null);

        List<StudentGroup> inputGroups = Arrays.asList(studentGroup, invalidGroup);
        List<StudentGroup> savedGroups = Collections.singletonList(studentGroup);
        when(studentGroupRepository.saveAll(anyList())).thenReturn(savedGroups);
        List<StudentGroup> result = studentGroupService.saveAll(inputGroups);
        assertEquals(1, result.size());
        assertEquals("334702", result.get(0).getName());
        verify(studentGroupRepository).saveAll(Collections.singletonList(studentGroup));
        verify(studentGroupCache).put(1L, studentGroup);
    }

    @Test
    void findAllWithCacheHitReturnsCachedGroups() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        when(studentGroup.getName()).thenReturn("334702");
        List<StudentGroup> cachedGroups = Collections.singletonList(studentGroup);
        when(studentGroupCache.getAll()).thenReturn(cachedGroups);
        List<StudentGroup> result = studentGroupService.findAll();
        assertEquals(1, result.size());
        assertEquals("334702", result.get(0).getName());
        verify(studentGroupCache).getAll();
        verify(studentGroupRepository, never()).findAll();
    }

    @Test
    void findAllWithCacheMissReturnsGroupsFromRepository() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        when(studentGroup.getName()).thenReturn("334702");
        List<StudentGroup> groups = Collections.singletonList(studentGroup);
        when(studentGroupCache.getAll()).thenReturn(Collections.emptyList());
        when(studentGroupRepository.findAll()).thenReturn(groups);
        List<StudentGroup> result = studentGroupService.findAll();
        assertEquals(1, result.size());
        assertEquals("334702", result.get(0).getName());
        verify(studentGroupCache).getAll();
        verify(studentGroupRepository).findAll();
        verify(studentGroupCache).putAll(groups);
    }

    @Test
    void findByIdWithCacheHitReturnsCachedGroup() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        when(studentGroup.getName()).thenReturn("334702");
        when(studentGroupCache.get(1L)).thenReturn(studentGroup);
        Optional<StudentGroup> result = studentGroupService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("334702", result.get().getName());
        verify(studentGroupCache).get(1L);
        verify(studentGroupRepository, never()).findById(anyLong());
    }

    @Test
    void findByIdWithCacheMissReturnsGroupFromRepository() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        when(studentGroup.getId()).thenReturn(1L);
        when(studentGroup.getName()).thenReturn("334702");
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
    void saveWithValidGroupReturnsSavedGroup() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        when(studentGroup.getId()).thenReturn(1L);
        when(studentGroup.getName()).thenReturn("334702");
        when(studentGroupRepository.save(studentGroup)).thenReturn(studentGroup);
        StudentGroup result = studentGroupService.save(studentGroup);
        assertNotNull(result);
        assertEquals("334702", result.getName());
        verify(studentGroupRepository).save(studentGroup);
        verify(studentGroupCache).put(1L, studentGroup);
    }

    @Test
    void updateWithExistingGroupReturnsUpdatedGroup() {
        StudentGroup studentGroup = mock(StudentGroup.class);
        StudentGroup updatedDetails = mock(StudentGroup.class);
        StudentGroup updatedGroup = mock(StudentGroup.class);
        when(studentGroup.getId()).thenReturn(1L);
        lenient().when(updatedDetails.getSpecialityName()).thenReturn("Радиосистемы и радиотехнологии");
        lenient().when(updatedDetails.getName()).thenReturn("334201");
        when(updatedGroup.getSpecialityName()).thenReturn("Радиосистемы и радиотехнологии");
        when(updatedGroup.getName()).thenReturn("334201");
        when(studentGroupRepository.findById(1L)).thenReturn(Optional.of(studentGroup));
        when(studentGroupRepository.save(any(StudentGroup.class))).thenReturn(updatedGroup);
        StudentGroup result = studentGroupService.update(1L, updatedDetails);
        assertEquals("334201", result.getName());
        assertEquals("Радиосистемы и радиотехнологии", result.getSpecialityName());
        verify(studentGroupRepository).findById(1L);
        verify(studentGroupRepository).save(studentGroup);
        verify(studentGroupCache).put(1L, updatedGroup);
    }

    @Test
    void updateWithNonExistingGroupThrowsException() {
        StudentGroup newDetails = mock(StudentGroup.class);
        when(studentGroupRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentGroupService.update(1L, newDetails));
        verify(studentGroupRepository).findById(1L);
        verify(studentGroupRepository, never()).save(any());
    }

    @Test
    void deleteWithExistingGroupDeletesSuccessfully() {
        studentGroupService.delete(1L);
        verify(studentGroupRepository).deleteById(1L);
        verify(studentGroupCache).invalidate(1L);
    }
}