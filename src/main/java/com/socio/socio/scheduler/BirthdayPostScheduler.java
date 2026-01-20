package com.socio.socio.scheduler;

import com.socio.socio.service.BirthdayJobService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BirthdayPostScheduler {

    private final BirthdayJobService birthdayJobService;

    public BirthdayPostScheduler(BirthdayJobService birthdayJobService) {
        this.birthdayJobService = birthdayJobService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void runBirthdayJob() {
        birthdayJobService.createBirthdayPosts();
    }
}
