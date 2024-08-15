package com.pnimac.urlshortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pnimac.urlshortner.model.UrlMapping;
import com.pnimac.urlshortner.repository.UrlRepository;
import com.pnimac.urlshortner.util.Base62Encoder;
import com.pnimac.urlshortner.util.MD5HashGenerator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlGenerationService {

	@Autowired
	private UrlRepository urlRepository;

	@Value("${app.url.base}")
	private String baseUrl;

	// Method to generate short URL using MD5 and Base62 encoding
	public UrlMapping createShortUrl(String longUrl, String customAlias, long expirationTime) {
		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setLongUrl(longUrl);
		urlMapping.setCreationDate(System.currentTimeMillis());
		urlMapping.setExpirationDate(expirationTime);
		String shortUrl;
		if (customAlias != null && !customAlias.isEmpty()) {
			// Check if custom alias already exists
			Optional<UrlMapping> existingAlias = urlRepository.findByCustomAlias(customAlias);
			if (existingAlias.isPresent()) {
				throw new RuntimeException("Custom alias already exists.");
			}
			urlMapping.setCustomAlias(customAlias);
			shortUrl = baseUrl + "/" + customAlias;
			urlMapping.setShortUrl(customAlias);
		} else {
			// Generate short URL code using MD5 and Base62
			shortUrl = generateShortUrl(longUrl);
			shortUrl = baseUrl + "/" + shortUrl;
			urlMapping.setShortUrl(shortUrl);
		}
		return urlRepository.save(urlMapping);
	}

	// Generates a short URL using MD5 and Base62
	private String generateShortUrl(String longUrl) {
		String md5Hash = MD5HashGenerator.generateMD5(longUrl);
		String hexSubstring = md5Hash.substring(0, 12); // first 6 bytes (12 hex characters)
		long decimalValue = Long.parseLong(hexSubstring, 16);
		return Base62Encoder.encode(decimalValue);// Encode the decimal value into Base62
	}
}
