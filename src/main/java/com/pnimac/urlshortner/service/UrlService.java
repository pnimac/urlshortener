package com.pnimac.urlshortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnimac.urlshortner.model.UrlMapping;
import com.pnimac.urlshortner.repository.UrlRepository;

import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    // Method to generate short URL (can use Base62 encoding or custom logic)
    public UrlMapping createShortUrl(String longUrl, String customAlias, long expirationTime) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setCreationDate(System.currentTimeMillis());
        urlMapping.setExpirationDate(expirationTime);

        if (customAlias != null && !customAlias.isEmpty()) {
            // Check if custom alias already exists
            Optional<UrlMapping> existingAlias = urlRepository.findByCustomAlias(customAlias);
            if (existingAlias.isPresent()) {
                throw new RuntimeException("Custom alias already exists.");
            }
            urlMapping.setCustomAlias(customAlias);
        } else {
            // Generate short URL code
            urlMapping.setId(generateShortUrl());
        }

        return urlRepository.save(urlMapping);
    }

    // Method to retrieve long URL for redirection
    public String getLongUrl(String shortUrl) {
        Optional<UrlMapping> urlMapping = urlRepository.findById(shortUrl);
        if (urlMapping.isPresent() && urlMapping.get().getExpirationDate() > System.currentTimeMillis()) {
            return urlMapping.get().getLongUrl();
        }
        throw new RuntimeException("URL not found or expired");
    }

    private String generateShortUrl() {
        // Implement Base62 encoding or random ID generation logic here
        return "abc123";
    }
}