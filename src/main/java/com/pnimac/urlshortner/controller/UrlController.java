package com.pnimac.urlshortner.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pnimac.urlshortner.data.UrlRequest;
import com.pnimac.urlshortner.data.UrlResponse;
import com.pnimac.urlshortner.model.UrlMapping;
import com.pnimac.urlshortner.service.UrlGenerationService;
import com.pnimac.urlshortner.service.UrlRedirectionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UrlController {

	private UrlGenerationService urlGenerationService;
	private UrlRedirectionService urlRedirectionService;

	@Autowired
	public UrlController(UrlGenerationService urlGenerationService, UrlRedirectionService urlRedirectionService) {
		this.urlGenerationService = urlGenerationService;
		this.urlRedirectionService = urlRedirectionService;
	}

	@PostMapping("/shorten")
	public UrlResponse shortenUrl(@RequestBody UrlRequest request) {
		UrlMapping urlMapping = urlGenerationService.createShortUrl(request.getLongUrl(), request.getCustomAlias(),
				request.getExpirationTime());
		String shortUrl = urlMapping.getShortUrl();
		return new UrlResponse(shortUrl);
	}

	@GetMapping("/{shortUrl}")
	public void redirectUrl(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String longUrl = urlRedirectionService.getLongUrl(shortUrl, request);
		response.sendRedirect(longUrl);
	}
}