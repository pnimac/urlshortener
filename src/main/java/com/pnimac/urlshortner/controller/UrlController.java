package com.pnimac.urlshortner.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pnimac.urlshortner.config.AppConfig;
import com.pnimac.urlshortner.data.UrlRequest;
import com.pnimac.urlshortner.data.UrlResponse;
import com.pnimac.urlshortner.model.UrlMapping;
import com.pnimac.urlshortner.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UrlController {

    private UrlService urlService;

    private AppConfig appConfig;

    @Autowired
    public UrlController(UrlService urlService, AppConfig appConfig) {
        this.urlService = urlService;
        this.appConfig = appConfig;
    }
    
    @PostMapping("/shorten")
    public UrlResponse shortenUrl(@RequestBody UrlRequest request) {
        UrlMapping urlMapping = urlService.createShortUrl(request.getLongUrl(), request.getCustomAlias(), request.getExpirationTime());
        String shortUrl = urlMapping.getShortUrl();
        return new UrlResponse(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirectUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String longUrl = urlService.getLongUrl(shortUrl);
        response.sendRedirect(longUrl);
    }
}