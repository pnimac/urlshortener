package com.pnimac.urlshortner.data;

public class UrlRequest {
    private String longUrl;        // The original long URL
    private String customAlias;    // Optional custom alias
    private long expirationTime;   // Expiration time (timestamp)

    // Getters and Setters
    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}

