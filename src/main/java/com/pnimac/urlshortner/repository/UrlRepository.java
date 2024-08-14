package com.pnimac.urlshortner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pnimac.urlshortner.model.UrlMapping;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<UrlMapping, String> {
    Optional<UrlMapping> findById(String id);
    Optional<UrlMapping> findByCustomAlias(String customAlias);
}