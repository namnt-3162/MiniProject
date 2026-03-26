package com.example.employee_management.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SystemTasks {
    private static final Logger logger = LoggerFactory.getLogger(SystemTasks.class);

    @Scheduled(fixedRate = 30000)
    public void reportSystemStatus() {
        logger.info("--- [System Task] System running at: {} ---", java.time.LocalDateTime.now());
    }
}
