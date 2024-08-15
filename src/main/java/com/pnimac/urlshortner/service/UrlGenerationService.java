package com.pnimac.urlshortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pnimac.urlshortner.model.UrlMapping;
import com.pnimac.urlshortner.repository.UrlRepository;
import com.pnimac.urlshortner.util.Base62Encoder;
import com.pnimac.urlshortner.util.MD5HashGenerator;
import com.pnimac.urlshortner.util.SaltGenerator;

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

	@Value("${url.shortener.default.expiration.days}")
	private long defaultExpirationDays;

	// Method to generate short URL using MD5 and Base62 encoding
	public UrlMapping createShortUrl(String longUrl, String customAlias, Long expirationTime) {
		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setLongUrl(longUrl);
		urlMapping.setCreationDate(System.currentTimeMillis());

		// Set default expiration date if none is provided
		if (expirationTime == null) {
			expirationTime = System.currentTimeMillis() + getDefaultExpirationPeriodMillis();
		}
		urlMapping.setExpirationDate(expirationTime);

		String salt = SaltGenerator.generateSalt(); // Generate a unique salt
		urlMapping.setSalt(salt); // Store the salt

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
			String saltedUrl = longUrl + salt; // Combine URL and salt
			shortUrl = generateShortUrl(saltedUrl);// Generate short URL code using MD5 and Base62
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

	public long getDefaultExpirationPeriodMillis() {
		return defaultExpirationDays * 24 * 60 * 60 * 1000;
	}
}
