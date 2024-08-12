package com.chikacow.pet_project.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
//@Component
public class ScheduleService {
    private volatile boolean isPaused = false;

    // Method to pause the scheduled task
    public void pauseTask() {
        isPaused = true;
    }

    // Method to resume the scheduled task
    public void resumeTask() {
        isPaused = false;
    }
    //@Async
    @Scheduled(fixedRate = 5000)
    public void performTask() {
        if (!isPaused) {
            System.out.println("Task performed at: " + System.currentTimeMillis());
            // Add your task logic here
        }
    }
}
