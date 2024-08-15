package com.pnimac.urlshortner.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pnimac.urlshortner.model.UrlMapping;
import com.pnimac.urlshortner.repository.UrlRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UrlRedirectionService {

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private AnalyticsService analyticsService;

    @Cacheable(value = "urls", key = "#shortUrl")
	public String getLongUrl(String shortUrl, HttpServletRequest request) {
		
    	Optional<UrlMapping> urlMapping = urlRepository.findByShortUrl(shortUrl);
		
		if (urlMapping.isPresent() && urlMapping.get().getExpirationDate() > System.currentTimeMillis()) {

			// Track the click
			String userAgent = request.getHeader("User-Agent");
			String referrer = request.getHeader("Referer");
			analyticsService.trackClick(shortUrl, userAgent, referrer);

			return urlMapping.get().getLongUrl();
		}
		throw new RuntimeException("URL not found or expired");
	}

}
