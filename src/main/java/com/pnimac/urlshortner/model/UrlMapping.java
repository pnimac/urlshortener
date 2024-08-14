package com.pnimac.urlshortner.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "urls")
public class UrlMapping {
    @Id
    private String id;  // Shortened URL code
    private String longUrl;  // Original URL
    private String customAlias;  // Custom alias (optional)
    private long creationDate;  // Timestamp
    private long expirationDate;  // Expiry date (optional)
}