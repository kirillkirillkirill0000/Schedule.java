package com.example.schedule.service;

import com.example.schedule.Cache.ScheduleCache;
import com.example.schedule.dao.ScheduleRepository;
import com.example.schedule.model.Schedule;
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
class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleCache scheduleCache;
    @InjectMocks
    private ScheduleService scheduleService;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setStartLessonTime("09:00");
        schedule.setEndLessonTime("10:20");
        schedule.setLessonTypeAbbrev("ЛК");
        schedule.setSubjectFullName("Схемотехника");
    }

    @Test
    void saveAll_ValidSchedules_ReturnsSavedSchedules() {
        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);
        schedule2.setStartLessonTime("10:35");
        schedule2.setEndLessonTime("11:55");
        schedule2.setLessonTypeAbbrev("ЛР");
        schedule2.setSubjectFullName("Программирование на языках высокого уровня");
        List<Schedule> inputSchedules = Arrays.asList(schedule, schedule2);
        List<Schedule> savedSchedules = Arrays.asList(schedule, schedule2);
        when(scheduleRepository.saveAll(anyList())).thenReturn(savedSchedules);
        List<Schedule> result = scheduleService.saveAll(inputSchedules);
        assertEquals(2, result.size());
        assertEquals("Схемотехника", result.get(0).getSubjectFullName());
        assertEquals("Программирование на языках высокого уровня", result.get(1).getSubjectFullName());
        verify(scheduleRepository).saveAll(inputSchedules);
        verify(scheduleCache, times(2)).put(anyLong(), any(Schedule.class));
    }

    @Test
    void saveAll_SchedulesWithNullFields_FiltersOutInvalid() {
        Schedule invalidSchedule = new Schedule();
        invalidSchedule.setId(2L);
        invalidSchedule.setStartLessonTime(null);
        invalidSchedule.setEndLessonTime("12:00");
        List<Schedule> inputSchedules = Arrays.asList(schedule, invalidSchedule);
        List<Schedule> savedSchedules = Collections.singletonList(schedule);
        when(scheduleRepository.saveAll(anyList())).thenReturn(savedSchedules);
        List<Schedule> result = scheduleService.saveAll(inputSchedules);
        assertEquals(1, result.size());
        assertEquals("Схемотехника", result.getFirst().getSubjectFullName());
        verify(scheduleRepository).saveAll(Collections.singletonList(schedule));
        verify(scheduleCache).put(1L, schedule);
    }

    @Test
    void save_ValidSchedule_ReturnsSavedSchedule() {
        when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Schedule result = scheduleService.save(schedule);
        assertNotNull(result);
        assertEquals("Схемотехника", result.getSubjectFullName());
        verify(scheduleRepository).save(schedule);
        verify(scheduleCache).put(1L, schedule);
    }

    @Test
    void findAll_CacheHit_ReturnsCachedSchedules() {
        List<Schedule> cachedSchedules = Collections.singletonList(schedule);
        when(scheduleCache.getAll()).thenReturn(cachedSchedules);
        List<Schedule> result = scheduleService.findAll();
        assertEquals(1, result.size());
        assertEquals("Схемотехника", result.getFirst().getSubjectFullName());
        verify(scheduleCache).getAll();
        verify(scheduleRepository, never()).findAll();
    }

    @Test
    void findAll_CacheMiss_ReturnsFromRepository() {
        List<Schedule> schedules = Collections.singletonList(schedule);
        when(scheduleCache.getAll()).thenReturn(Collections.emptyList());
        when(scheduleRepository.findAll()).thenReturn(schedules);
        List<Schedule> result = scheduleService.findAll();
        assertEquals(1, result.size());
        assertEquals("Схемотехника", result.getFirst().getSubjectFullName());
        verify(scheduleCache).getAll();
        verify(scheduleRepository).findAll();
        verify(scheduleCache).putAll(schedules);
    }

    @Test
    void findById_CacheHit_ReturnsCachedSchedule() {
        when(scheduleCache.get(1L)).thenReturn(schedule);
        Optional<Schedule> result = scheduleService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Схемотехника", result.get().getSubjectFullName());
        verify(scheduleCache).get(1L);
        verify(scheduleRepository, never()).findById(anyLong());
    }

    @Test
    void findById_CacheMiss_ReturnsFromRepository() {
        when(scheduleCache.get(1L)).thenReturn(null);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        Optional<Schedule> result = scheduleService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Схемотехника", result.get().getSubjectFullName());
        verify(scheduleCache).get(1L);
        verify(scheduleRepository).findById(1L);
        verify(scheduleCache).put(1L, schedule);
    }

    @Test
    void update_ScheduleExists_ReturnsUpdatedSchedule() {
        Schedule updatedDetails = new Schedule();
        updatedDetails.setStartLessonTime("15:50");
        updatedDetails.setEndLessonTime("17:10");
        updatedDetails.setLessonTypeAbbrev("ЛР");
        updatedDetails.setSubjectFullName("Основы машинного обучения");
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        Schedule result = scheduleService.update(1L, updatedDetails);
        assertEquals("Основы машинного обучения", result.getSubjectFullName());
        assertEquals("ЛР", result.getLessonTypeAbbrev());
        verify(scheduleRepository).findById(1L);
        verify(scheduleRepository).save(schedule);
        verify(scheduleCache).put(1L, schedule);
    }

    @Test
    void update_ScheduleNotFound_ThrowsException() {
        Schedule updatedDetails = new Schedule();
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> scheduleService.update(1L, updatedDetails));
        verify(scheduleRepository).findById(1L);
        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void delete_ScheduleExists_DeletesSuccessfully() {
        scheduleService.delete(1L);
        verify(scheduleRepository).deleteById(1L);
        verify(scheduleCache).invalidate(1L);
    }

    @Test
    void findByLessonTypeAndSubject_CacheHit_ReturnsCachedSchedules() {
        List<Schedule> cachedSchedules = Collections.singletonList(schedule);
        String cacheKey = "ЛК:Схемотехника";
        when(scheduleCache.getByCustomKey(cacheKey)).thenReturn(cachedSchedules);
        List<Schedule> result = scheduleService.findByLessonTypeAndSubject("ЛК", "Схемотехника");
        assertEquals(1, result.size());
        assertEquals("Схемотехника", result.getFirst().getSubjectFullName());
        verify(scheduleCache).getByCustomKey(cacheKey);
        verify(scheduleRepository, never()).findByLessonTypeAndSubjectFullName(anyString(), anyString());
    }

    @Test
    void findByLessonTypeAndSubject_CacheMiss_ReturnsFromRepository() {
        List<Schedule> schedules = Collections.singletonList(schedule);
        String cacheKey = "ЛК:Схемотехника";
        when(scheduleCache.getByCustomKey(cacheKey)).thenReturn(null);
        when(scheduleRepository.findByLessonTypeAndSubjectFullName("ЛК", "Схемотехника")).thenReturn(schedules);
        List<Schedule> result = scheduleService.findByLessonTypeAndSubject("ЛК", "Схемотехника");
        assertEquals(1, result.size());
        assertEquals("Схемотехника", result.getFirst().getSubjectFullName());
        verify(scheduleCache).getByCustomKey(cacheKey);
        verify(scheduleRepository).findByLessonTypeAndSubjectFullName("ЛК", "Схемотехника");
        verify(scheduleCache).putByCustomKey(cacheKey, schedules);
    }
}