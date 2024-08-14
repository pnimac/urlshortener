package com.pnimac.urlshortner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Value("${app.url.base}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
