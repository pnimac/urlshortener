package com.pnimac.urlshortner.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnimac.urlshortner.model.UrlClick;
import com.pnimac.urlshortner.repository.UrlClickRepository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Analytics we are tracking:
Click Tracking: Track how many times a shortened URL has been clicked.
Geographic Insights: Track the geographic location of users who click the shortened URLs.
Referrer Information: Track where the click is coming from (e.g., direct visit, social media, email). */

@Service
public class AnalyticsService {

    @Autowired
    private UrlClickRepository urlClickRepository;

    public void trackClick(String shortUrl, String userAgent, String referrer) {
        // Record the click event
        UrlClick urlClick = new UrlClick();
        urlClick.setShortUrl(shortUrl);
        urlClick.setTimestamp(Instant.now().toEpochMilli());
        urlClick.setUserAgent(userAgent);
        urlClick.setReferrer(referrer);
        
		String geoLocation = getGeoLocation(); // Implement geo-location based on IP or other means
        urlClick.setGeoLocation(geoLocation);

        urlClickRepository.save(urlClick);
    }

    private String getGeoLocation() {
		// Implement geo-location lookup based on IP address
		// For simplicity, this method returns a placeholder value
		return "Australia";
	}
}
