package com.pnimac.urlshortner.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pnimac.urlshortner.repository.UrlRepository;

import java.util.Date;

@Component
public class UrlPurgeScheduler {

    @Autowired
    private UrlRepository urlRepository;

    // Schedule the task to run every hour
    @Scheduled(cron = "0 0 * * * ?")
    public void purgeExpiredUrls() {
        // Get the current time
        long currentTime = System.currentTimeMillis();

        // Find expired URLs (assuming expirationDate is stored as a timestamp)
        urlRepository.deleteByExpirationDateBefore(currentTime);

        // Log or print a message for confirmation (optional)
        System.out.println("Purged expired URLs at " + new Date());
    }
}