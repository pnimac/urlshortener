package com.pnimac.urlshortner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pnimac.urlshortner.model.UrlClick;

public interface UrlClickRepository extends MongoRepository<UrlClick, String> {

}
