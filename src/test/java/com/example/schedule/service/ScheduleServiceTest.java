package com.example.schedule.service;

import com.example.schedule.Cache.ScheduleCache;
import com.example.schedule.dao.ScheduleRepository;
import com.example.schedule.model.Schedule;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleCache scheduleCache;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void saveAllWithValidSchedulesReturnsSavedSchedules() {
        Schedule schedule = mock(Schedule.class);
        Schedule schedule2 = mock(Schedule.class);
        when(schedule.getId()).thenReturn(1L);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
        when(schedule.getStartLessonTime()).thenReturn("09:00");
        when(schedule.getEndLessonTime()).thenReturn("10:20");
        when(schedule2.getId()).thenReturn(2L);
        when(schedule2.getSubjectFullName()).thenReturn("Программирование на языках высокого уровня");
        when(schedule2.getStartLessonTime()).thenReturn("10:35");
        when(schedule2.getEndLessonTime()).thenReturn("11:55");

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
    void saveAllWithNullFieldsFiltersOutInvalidSchedules() {
        Schedule schedule = mock(Schedule.class);
        Schedule invalidSchedule = mock(Schedule.class);
        when(schedule.getId()).thenReturn(1L);
        when(schedule.getStartLessonTime()).thenReturn("09:00");
        when(schedule.getEndLessonTime()).thenReturn("10:20");
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
        when(invalidSchedule.getId()).thenReturn(2L);
        lenient().when(invalidSchedule.getStartLessonTime()).thenReturn(null);

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
    void saveWithValidScheduleReturnsSavedSchedule() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getId()).thenReturn(1L);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
        when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Schedule result = scheduleService.save(schedule);
        assertNotNull(result);
        assertEquals("Схемотехника", result.getSubjectFullName());
        verify(scheduleRepository).save(schedule);
        verify(scheduleCache).put(1L, schedule);
    }

    @Test
    void findAllWithCacheHitReturnsCachedSchedules() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
        List<Schedule> cachedSchedules = Collections.singletonList(schedule);
        when(scheduleCache.getAll()).thenReturn(cachedSchedules);
        List<Schedule> result = scheduleService.findAll();
        assertEquals(1, result.size());
        assertEquals("Схемотехника", result.getFirst().getSubjectFullName());
        verify(scheduleCache).getAll();
        verify(scheduleRepository, never()).findAll();
    }

    @Test
    void findAllWithCacheMissReturnsSchedulesFromRepository() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
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
    void findByIdWithCacheHitReturnsCachedSchedule() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
        when(scheduleCache.get(1L)).thenReturn(schedule);
        Optional<Schedule> result = scheduleService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Схемотехника", result.get().getSubjectFullName());
        verify(scheduleCache).get(1L);
        verify(scheduleRepository, never()).findById(anyLong());
    }

    @Test
    void findByIdWithCacheMissReturnsScheduleFromRepository() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getId()).thenReturn(1L);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
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
    void updateWithExistingScheduleReturnsUpdatedSchedule() {
        Schedule schedule = mock(Schedule.class);
        Schedule updatedDetails = mock(Schedule.class);
        Schedule updatedSchedule = mock(Schedule.class);
        when(schedule.getId()).thenReturn(1L);
        lenient().when(updatedDetails.getLessonTypeAbbrev()).thenReturn("ЛР");
        lenient().when(updatedDetails.getSubjectFullName()).thenReturn("Основы машинного обучения");
        when(updatedSchedule.getLessonTypeAbbrev()).thenReturn("ЛР");
        when(updatedSchedule.getSubjectFullName()).thenReturn("Основы машинного обучения");
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(updatedSchedule);
        Schedule result = scheduleService.update(1L, updatedDetails);
        assertEquals("Основы машинного обучения", result.getSubjectFullName());
        assertEquals("ЛР", result.getLessonTypeAbbrev());
        verify(scheduleRepository).findById(1L);
        verify(scheduleRepository).save(schedule);
        verify(scheduleCache).put(1L, updatedSchedule);
    }

    @Test
    void updateWithNonExistingScheduleThrowsException() {
        Schedule newDetails = mock(Schedule.class);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> scheduleService.update(1L, newDetails));
        verify(scheduleRepository).findById(1L);
        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void deleteWithExistingScheduleDeletesSuccessfully() {
        scheduleService.delete(1L);
        verify(scheduleRepository).deleteById(1L);
        verify(scheduleCache).invalidate(1L);
    }

    @Test
    void findByLessonTypeAndSubjectWithCacheHitReturnsCachedSchedules() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
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
    void findByLessonTypeAndSubjectWithCacheMissReturnsSchedulesFromRepository() {
        Schedule schedule = mock(Schedule.class);
        when(schedule.getSubjectFullName()).thenReturn("Схемотехника");
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