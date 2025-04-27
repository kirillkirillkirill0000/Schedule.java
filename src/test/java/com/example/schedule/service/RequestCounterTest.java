package com.example.schedule.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestCounterTest {

    @Test
    void testIncrementAndGet() {
        RequestCounter counter = new RequestCounter();
        assertEquals(0, counter.getCount());
        counter.increment();
        assertEquals(1, counter.getCount());
        counter.increment();
        assertEquals(2, counter.getCount());
    }

    @Test
    void testReset() {
        RequestCounter counter = new RequestCounter();
        counter.increment();
        counter.increment();
        assertEquals(2, counter.getCount());
        counter.reset();
        assertEquals(0, counter.getCount());
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        final RequestCounter counter = new RequestCounter();
        final int threadsCount = 100;
        final int incrementsPerThread = 1000;

        Thread[] threads = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        assertEquals(threadsCount * incrementsPerThread, counter.getCount());
    }
}